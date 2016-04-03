package com.example.edge.common.bindingAdapters

import android.databinding.BindingAdapter
import android.support.v7.widget.Toolbar
import com.example.edge.common.ActivityOwnerComponent
import com.example.edge.common.DaggerService.Companion.getComponent
import com.example.edge.drawer.DrawerComponent

object ToolbarBindingAdapters {
    @BindingAdapter(value = *arrayOf("hasHome", "hasUp"), requireAll = false)
    @JvmStatic fun bindToolbarAndMenu(toolbar: Toolbar, hasHome: Boolean, hasUp: Boolean) {
        val drawerPresenter = toolbar.context.getComponent(DrawerComponent::class)?.getDrawerPresenter()

        if (drawerPresenter != null) {
            drawerPresenter.setToolbar(toolbar, hasHome, hasUp)
        } else {
            val activityOwner = toolbar.context.getComponent(ActivityOwnerComponent::class)?.getActivityOwner()
            activityOwner?.setToolbar(toolbar, hasHome, hasUp)
        }
    }
}
