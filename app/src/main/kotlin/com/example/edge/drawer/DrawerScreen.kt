package com.example.edge.drawer

import com.example.edge.R
import com.example.edge.common.HasPresenter
import com.example.edge.common.Layout
import com.example.edge.common.WithComponent
import flow.ClassKey
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
@WithComponent(DrawerComponent::class)
@Layout(R.layout.drawer_container)
class DrawerScreen : ClassKey(), PaperParcelable, HasPresenter<DrawerPresenter> {
    override fun getPresenter(component: Any): DrawerPresenter {
        return (component as DrawerComponent).getDrawerPresenter()
    }
}
