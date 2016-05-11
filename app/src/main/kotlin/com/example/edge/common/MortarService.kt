package com.example.edge.common

import android.content.Context
import flow.Flow
import flow.Services
import flow.ServicesFactory
import mortar.MortarScope

class MortarService(private val rootScope: MortarScope) : ServicesFactory() {
    override fun bindServices(services: Services.Binder) {
        val key = services.getKey<Any>()
        if (key !is WithScope) {
            return
        }

        val scopeName = key.javaClass.name
        val parentScope = services.getService<MortarScope>(SERVICE_NAME) ?: rootScope
        val childScope = parentScope.findChild(scopeName) ?: key.createScope(parentScope).build(scopeName)

        services.bind(SERVICE_NAME, childScope)
    }

    override fun tearDownServices(services: Services) {
        services.getService<MortarScope>(SERVICE_NAME)?.destroy()
        super.tearDownServices(services)
    }

    interface WithScope {
        fun createScope(parentScope: MortarScope): MortarScope.Builder
    }

    companion object {
        const val SERVICE_NAME = "MortarService"

        fun <T> get(context: Context) = Flow.getService<T>(SERVICE_NAME, context)
    }
}
