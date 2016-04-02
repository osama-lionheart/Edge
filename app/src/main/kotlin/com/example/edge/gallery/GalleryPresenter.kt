package com.example.edge.gallery

import android.support.v7.widget.Toolbar
import android.view.View
import com.example.edge.R
import com.example.edge.common.HandlesBack
import com.example.edge.common.Presenter
import com.example.edge.common.ScopeSingleton
import com.example.edge.drawer.DrawerPresenter
import javax.inject.Inject

@ScopeSingleton(GalleryComponent::class)
class GalleryPresenter @Inject constructor(private val drawerPresenter: DrawerPresenter) :
        Presenter<View>, HandlesBack {
    private var view: View? = null

    override fun attach(view: View) {
        this.view = view

        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        drawerPresenter.setToolbar(toolbar, true, false)
    }

    override fun detach(view: View) {
        this.view = null
    }

    override fun onBackPressed(): Boolean {
        return false;
    }
}
