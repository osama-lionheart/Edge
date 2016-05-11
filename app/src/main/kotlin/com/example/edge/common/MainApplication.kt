package com.example.edge.common

import android.app.Application
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner

class MainApplication : Application() {
    val services: MutableMap<String, Any> = mutableMapOf()

    override fun getSystemService(name: String?): Any? {
        var scope = buildMortarScope()

        if (scope.hasService(name)) {
            return scope.getService(name)
        }

        return super.getSystemService(name)
    }

    protected fun buildMortarScope(): MortarScope {
        return MortarScope.buildRootScope()
                .withService(BundleServiceRunner.SERVICE_NAME, BundleServiceRunner())
                .withService(DaggerService.SERVICE_NAME, services)
                .build("Root")
    }
}
