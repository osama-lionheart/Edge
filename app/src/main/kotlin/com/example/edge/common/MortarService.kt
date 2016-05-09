package com.example.edge.common

import android.content.Context
import flow.Flow
import flow.Services
import flow.ServicesFactory
import mortar.MortarScope

class MortarService(private val rootScope: MortarScope) : ServicesFactory() {
    override fun bindServices(services: Services.Binder) {
        val key = services.getKey<Any>()
        var childScope = rootScope.findChild(key.javaClass.simpleName)
        if (childScope == null) {
            childScope = rootScope.buildChild()
                    .build(key.javaClass.simpleName)
        }
        services.bind(SERVICE_NAME, childScope)
    }

    override fun tearDownServices(services: Services) {
        services.getService<MortarScope>(SERVICE_NAME)?.destroy()
        super.tearDownServices(services)
    }

    companion object {
        const val SERVICE_NAME = "MortarService"

        fun <T> get(context: Context) = Flow.getService<T>(SERVICE_NAME, context)
    }
}
