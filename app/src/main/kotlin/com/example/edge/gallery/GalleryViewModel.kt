package com.example.edge.gallery

import android.databinding.ObservableField
import com.example.edge.common.ScopeSingleton
import javax.inject.Inject

@ScopeSingleton(GalleryComponent::class)
class GalleryViewModel @Inject constructor() {
    val description: String = "Hello, world!"
}
