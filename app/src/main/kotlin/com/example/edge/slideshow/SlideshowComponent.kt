package com.example.edge.slideshow

import com.example.edge.common.ScopeSingleton
import com.example.edge.drawer.DrawerComponent
import dagger.Component

@ScopeSingleton(SlideshowComponent::class)
@Component(dependencies = arrayOf(DrawerComponent::class))
interface SlideshowComponent : DrawerComponent {
    fun getSlideshowPresenter(): SlideshowPresenter
}
