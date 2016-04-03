package com.example.edge.gallery

import android.view.View
import com.example.edge.common.Presenter
import com.example.edge.common.ScopeSingleton
import javax.inject.Inject

@ScopeSingleton(GalleryComponent::class)
class GalleryPresenter @Inject constructor() : Presenter<View> {
    private var view: View? = null

    override fun attach(view: View) {
        this.view = view
    }

    override fun detach(view: View) {
        this.view = null
    }
}
