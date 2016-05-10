package com.example.edge.common

import android.content.Context
import android.util.Log
import flow.Flow
import flow.Services
import flow.ServicesFactory
import kotlin.reflect.KClass

class DaggerService(private val component: Any) : ServicesFactory() {
    private val cachedServices: MutableMap<Any, Any> = mutableMapOf()

    companion object {
        const val SERVICE_NAME = "DAGGER_SERVICE"

        fun <T : Any> Context.getComponent(klass: KClass<T>): T? {
            val component = Flow.getService<T>(SERVICE_NAME, this)

            if (component == null || !klass.java.isAssignableFrom(component.javaClass)) {
                return null
            }

            return component
        }
    }

    override fun bindServices(services: Services.Binder) {
        val key = services.getKey<Any>()!!
        val componentClass = key.javaClass.getAnnotation(WithComponent::class.java)?.value

        if (componentClass != null) {
            val parentComponent = when (key) {
                is HasContainerKey -> cachedServices[key.containerKey]!!
                else -> component
            }

            val keyComponent = createComponent(componentClass.java, parentComponent)
            services.bind(SERVICE_NAME, keyComponent)
            cachedServices.put(key, keyComponent)
        }

        Log.e("EDGE", "bindServices: " + key.javaClass.simpleName);
    }

    override fun tearDownServices(services: Services) {
        super.tearDownServices(services)
        cachedServices.remove(services.getKey<Any>())

        Log.e("EDGE", "tearDownServices: " + services.getKey<Any>().javaClass.simpleName);
    }

    fun <T> createComponent(componentClass: Class<T>, vararg dependencies: Any): T {
        val packageName = componentClass.`package`.name
        val simpleName = componentClass.name.substring(packageName.length + 1)
        val generatedName = "$packageName.Dagger$simpleName".replace("$", "_")

        try {
            val generatedClass = Class.forName(generatedName)
            val builder = generatedClass.getMethod("builder").invoke(null)

            for (method in builder.javaClass.declaredMethods) {
                val params = method.parameterTypes

                if (params.size == 1) {
                    val dependencyClass = params[0]

                    for (dependency in dependencies) {
                        if (dependencyClass.isAssignableFrom(dependency.javaClass)) {
                            method.invoke(builder, dependency)
                            break;
                        }
                    }
                }
            }

            @Suppress("UNCHECKED_CAST")
            return builder.javaClass.getMethod("build").invoke(builder) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
