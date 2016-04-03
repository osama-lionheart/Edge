package com.example.edge.slideshow

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
@WithComponent(SlideshowComponent::class)
@Layout(R.layout.slideshow_screen)
class SlideshowScreen : HasContainerKey(DrawerScreen()), HasPresenter<SlideshowPresenter> {
    override fun getPresenter(context: Context): SlideshowPresenter {
        return context.getComponent(SlideshowComponent::class)!!.getSlideshowPresenter()
    }
}
