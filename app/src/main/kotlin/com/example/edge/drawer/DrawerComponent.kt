package com.example.edge.drawer

import com.example.edge.common.MainActivityComponent
import com.example.edge.common.ScopeSingleton
import dagger.Component

@ScopeSingleton(DrawerComponent::class)
@Component(dependencies = arrayOf(MainActivityComponent::class))
interface DrawerComponent {
    fun getPresenter(): DrawerPresenter
}
