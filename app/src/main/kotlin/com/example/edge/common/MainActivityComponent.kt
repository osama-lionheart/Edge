package com.example.edge.common

import dagger.Component

@ScopeSingleton(MainActivityComponent::class)
@Component
interface MainActivityComponent : ActivityOwnerComponent {
    fun inject(activity: MainActivity)
}
