package com.example.edge.gallery

import android.util.Log
import android.view.View
import com.example.edge.common.ScopeSingleton
import mortar.MortarScope
import mortar.ViewPresenter
import javax.inject.Inject

@ScopeSingleton(GalleryComponent::class)
class GalleryPresenter @Inject constructor() : ViewPresenter<View>() {
    public override fun onEnterScope(scope: MortarScope) {
        Log.e("EDGE", "onEnterScope " + javaClass.simpleName);
    }

    public override fun onExitScope() {
        Log.e("EDGE", "onExitScope " + javaClass.simpleName);
    }
}
