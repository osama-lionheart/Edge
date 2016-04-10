package com.example.edge.gallery

import com.example.edge.common.ScopeSingleton
import com.example.edge.drawer.DrawerComponent
import dagger.Component

@ScopeSingleton(GalleryComponent::class)
@Component(dependencies = arrayOf(DrawerComponent::class))
interface GalleryComponent : DrawerComponent {
    fun getGalleryPresenter(): GalleryPresenter
    fun getGalleryViewModel(): GalleryViewModel
}
