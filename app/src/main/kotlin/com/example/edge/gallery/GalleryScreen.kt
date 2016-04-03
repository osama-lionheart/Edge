package com.example.edge.gallery

import android.content.Context
import com.example.edge.R
import com.example.edge.common.DaggerService.Companion.getComponent
import com.example.edge.common.HasContainerKey
import com.example.edge.common.HasPresenter
import com.example.edge.common.Layout
import com.example.edge.common.WithComponent
import com.example.edge.drawer.DrawerScreen
import nz.bradcampbell.paperparcel.PaperParcel

@PaperParcel
@Layout(R.layout.gallery_screen)
@WithComponent(GalleryComponent::class)
class GalleryScreen() : HasContainerKey(DrawerScreen()), HasPresenter<GalleryPresenter> {
    override fun getPresenter(context: Context): GalleryPresenter {
        return context.getComponent(GalleryComponent::class)!!.getGalleryPresenter()
    }
}
