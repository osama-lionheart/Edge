package com.example.edge.slideshow

import android.content.Context
import com.example.edge.R
import com.example.edge.common.*
import com.example.edge.common.DaggerService.Companion.getComponent
import com.example.edge.drawer.DrawerScreen
import mortar.MortarScope
import nz.bradcampbell.paperparcel.PaperParcel

@PaperParcel
@WithComponent(SlideshowComponent::class)
@Layout(R.layout.slideshow_screen)
class SlideshowScreen : HasContainerKey(DrawerScreen()), HasPresenter<SlideshowPresenter>, MortarService.WithScope {
    override fun createScope(parentScope: MortarScope): MortarScope.Builder {
        return parentScope.buildChild();
    }

    override fun getPresenter(context: Context): SlideshowPresenter {
        return context.getComponent(SlideshowComponent::class)!!.getSlideshowPresenter()
    }
}
