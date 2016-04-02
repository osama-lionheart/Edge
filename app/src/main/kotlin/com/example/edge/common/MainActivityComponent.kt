package com.example.edge.common

import dagger.Component

@ScopeSingleton(MainActivityComponent::class)
@Component
interface MainActivityComponent {
    fun inject(activity: MainActivity)

    fun getActivityOwner(): ActivityOwner
}
