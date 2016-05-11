package com.example.edge.common

import android.view.View

interface HasPresenter<out T : Presenter<View>> {
    fun getPresenter(component: Any): T
}
