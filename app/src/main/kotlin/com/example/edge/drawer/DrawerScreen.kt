package com.example.edge.drawer

import android.content.Context
import com.example.edge.R
import com.example.edge.common.DaggerService.Companion.getComponent
import com.example.edge.common.HasPresenter
import com.example.edge.common.Layout
import com.example.edge.common.MortarService
import com.example.edge.common.WithComponent
import flow.ClassKey
import mortar.MortarScope
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
@WithComponent(DrawerComponent::class)
@Layout(R.layout.drawer_container)
class DrawerScreen : ClassKey(), PaperParcelable, HasPresenter<DrawerPresenter>, MortarService.WithScope {
    override fun createScope(parentScope: MortarScope): MortarScope.Builder {
        return parentScope.buildChild();
    }

    override fun getPresenter(context: Context): DrawerPresenter {
        return context.getComponent(DrawerComponent::class)!!.getDrawerPresenter()
    }
}
