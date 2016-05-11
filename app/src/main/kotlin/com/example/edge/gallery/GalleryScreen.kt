package com.example.edge.gallery

import com.example.edge.R
import com.example.edge.common.*
import com.example.edge.drawer.DrawerScreen
import nz.bradcampbell.paperparcel.PaperParcel

@PaperParcel
@Layout(R.layout.gallery_screen)
@WithViewModel(GalleryViewModel::class)
@WithComponent(GalleryComponent::class)
class GalleryScreen() : HasContainerKey(DrawerScreen()), HasPresenter<GalleryPresenter> {
    override fun getPresenter(component: Any): GalleryPresenter {
        return (component as GalleryComponent).getGalleryPresenter()
    }
}
