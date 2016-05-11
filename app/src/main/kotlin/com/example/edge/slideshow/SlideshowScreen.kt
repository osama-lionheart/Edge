package com.example.edge.slideshow

import com.example.edge.R
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
    override fun getPresenter(component: Any): SlideshowPresenter {
        return (component as SlideshowComponent).getSlideshowPresenter()
    }
}
