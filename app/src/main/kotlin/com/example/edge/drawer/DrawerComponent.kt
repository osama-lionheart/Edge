package com.example.edge.drawer

import com.example.edge.common.ActivityOwnerComponent
import com.example.edge.common.MainActivityComponent
import com.example.edge.common.ScopeSingleton
import dagger.Component

@ScopeSingleton(DrawerComponent::class)
@Component(dependencies = arrayOf(MainActivityComponent::class))
interface DrawerComponent : ActivityOwnerComponent {
    fun getDrawerPresenter(): DrawerPresenter
}
