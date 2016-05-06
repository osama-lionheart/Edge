package com.example.edge.gallery

import android.view.View
import com.example.edge.common.ScopeSingleton
import mortar.ViewPresenter
import javax.inject.Inject

@ScopeSingleton(GalleryComponent::class)
class GalleryPresenter @Inject constructor() : ViewPresenter<View>() {
}
