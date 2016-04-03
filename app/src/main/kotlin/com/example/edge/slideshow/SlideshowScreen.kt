package com.example.edge.slideshow

import android.content.Context
import com.example.edge.R
import com.example.edge.common.DaggerService.Companion.getComponent
import com.example.edge.common.HasPresenter
import com.example.edge.common.Layout
import com.example.edge.common.WithComponent
import flow.ClassKey
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
@WithComponent(SlideshowComponent::class)
@Layout(R.layout.slideshow_screen)
class SlideshowScreen : ClassKey(), PaperParcelable, HasPresenter<SlideshowPresenter> {
    override fun getPresenter(context: Context): SlideshowPresenter {
        return context.getComponent<SlideshowComponent>()!!.getPresenter()
    }
}
