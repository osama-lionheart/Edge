package com.example.edge.slideshow

import android.view.View
import com.example.edge.common.HandlesBack
import com.example.edge.common.ScopeSingleton
import mortar.ViewPresenter
import javax.inject.Inject

@ScopeSingleton(SlideshowComponent::class)
class SlideshowPresenter @Inject constructor() : ViewPresenter<View>(), HandlesBack {
    override fun onBackPressed(): Boolean {
        return false
    }
}
