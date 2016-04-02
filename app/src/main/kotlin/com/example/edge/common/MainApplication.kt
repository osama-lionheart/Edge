package com.example.edge.common

import android.app.Application

class MainApplication : Application() {
    val services: MutableMap<String, Any> = mutableMapOf()

    override fun onCreate() {
        super.onCreate()
    }

    override fun getSystemService(name: String?): Any? {
        if (DaggerService.SERVICE_NAME.equals(name)) {
            return services;
        }

        return super.getSystemService(name)
    }
}
