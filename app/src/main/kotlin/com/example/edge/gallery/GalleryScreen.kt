package com.example.edge.gallery

import android.content.Context
import com.example.edge.R
import com.example.edge.common.*
import com.example.edge.common.DaggerService.Companion.getComponent
import com.example.edge.drawer.DrawerScreen
import mortar.MortarScope
import nz.bradcampbell.paperparcel.PaperParcel

@PaperParcel
@Layout(R.layout.gallery_screen)
@WithViewModel(GalleryViewModel::class)
@WithComponent(GalleryComponent::class)
class GalleryScreen() : HasContainerKey(DrawerScreen()), HasPresenter<GalleryPresenter>, MortarService.WithScope {
    override fun createScope(parentScope: MortarScope): MortarScope.Builder {
        return parentScope.buildChild();
    }

    override fun getPresenter(context: Context): GalleryPresenter {
        return context.getComponent(GalleryComponent::class)!!.getGalleryPresenter()
    }
}
