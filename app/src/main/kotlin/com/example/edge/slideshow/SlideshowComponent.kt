package com.example.edge.slideshow

import com.example.edge.common.MainActivityComponent
import com.example.edge.common.ScopeSingleton
import dagger.Component

@ScopeSingleton(SlideshowComponent::class)
@Component(dependencies = arrayOf(MainActivityComponent::class))
interface SlideshowComponent {
    fun getPresenter(): SlideshowPresenter
}
