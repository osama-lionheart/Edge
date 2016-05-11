package com.example.edge.slideshow

import android.util.Log
import android.view.View
import com.example.edge.common.HandlesBack
import com.example.edge.common.ScopeSingleton
import mortar.MortarScope
import mortar.ViewPresenter
import javax.inject.Inject

@ScopeSingleton(SlideshowComponent::class)
class SlideshowPresenter @Inject constructor() : ViewPresenter<View>(), HandlesBack {
    override fun onBackPressed(): Boolean {
        return false
    }

    public override fun onEnterScope(scope: MortarScope) {
        Log.e("EDGE", "onEnterScope " + javaClass.simpleName);
    }

    public override fun onExitScope() {
        Log.e("EDGE", "onExitScope " + javaClass.simpleName);
    }
}
