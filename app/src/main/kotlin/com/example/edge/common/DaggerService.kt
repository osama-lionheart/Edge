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

        Log.e("EDGE", "bindServices for ${key.javaClass.simpleName}")

        if (componentClass != null) {
            val parentComponent = when (key) {
                is HasContainerKey -> cachedServices[key.containerKey]!!
                else -> component
            }

            val keyComponent = createComponent(componentClass.java, parentComponent)
            services.bind(SERVICE_NAME, keyComponent)
            cachedServices.put(key, keyComponent)

            if (key is HasPresenter<*>) {
                val presenter = key.getPresenter(keyComponent);
                presenter.onEnter();
            }
        }
    }

    override fun tearDownServices(services: Services) {
        super.tearDownServices(services)
        val key = services?.getKey<Any>()!!
        val keyComponent = services?.getService<Any>(SERVICE_NAME)!!

        Log.e("EDGE", "tearDownServices for ${key.javaClass.simpleName}")

        if (key is HasPresenter<*>) {
            val presenter = key.getPresenter(keyComponent);
            presenter.onExit();
        }

        cachedServices.remove(key)
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
