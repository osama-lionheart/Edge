package com.example.edge.common

import android.content.Context
import android.view.View

interface HasPresenter<out T : Presenter<in View>> {
    fun getPresenter(context: Context): T
}