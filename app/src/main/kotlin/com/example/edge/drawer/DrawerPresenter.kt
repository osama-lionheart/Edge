package com.example.edge.drawer

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import com.example.edge.R
import com.example.edge.common.*
import com.example.edge.common.DaggerService.Companion.getComponent
import com.example.edge.gallery.GalleryScreen
import com.example.edge.slideshow.SlideshowScreen
import flow.Direction
import flow.Flow
import flow.History
import javax.inject.Inject

@ScopeSingleton(DrawerComponent::class)
class DrawerPresenter @Inject constructor(
        private val activityOwner: ActivityOwner) : Presenter<View>, HandlesBack {
    private var view: DrawerLayout? = null
    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onEnter() {
        Log.e("EDGE", "DrawerPresenter.onEnter")
    }

    override fun attach(view: View) {
        this.view = view as DrawerLayout

        val navigationView = view.findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(onNavigationItemSelected)

        activityOwner.onOptionsItemsSelectedListener = {
            if (drawerToggle?.onOptionsItemSelected(it) ?: false) {
                true
            } else if (it.itemId == android.R.id.home) {
                activityOwner.activity?.onBackPressed()
                true
            }

            false
        }
    }

    override fun detach(view: View) {
        this.view = null
        activityOwner.onOptionsItemsSelectedListener = null
    }

    override fun onExit() {
        Log.e("EDGE", "DrawerPresenter.onExit")
    }

    val onNavigationItemSelected: (MenuItem) -> Boolean = {
        view?.closeDrawer(GravityCompat.START)

        when (it.itemId) {
            R.id.nav_gallery -> Flow.get(view as View).setHistory(History.single(GalleryScreen()), Direction.REPLACE)
            R.id.nav_slideshow -> Flow.get(view as View).set(SlideshowScreen())
        }

        true
    }

    fun setToolbar(toolbar: Toolbar, hasHome: Boolean, hasUp: Boolean) {
        activityOwner.activity?.setSupportActionBar(toolbar)

        drawerToggle = ActionBarDrawerToggle(
                activityOwner.activity, view!!, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        view!!.addDrawerListener(drawerToggle!!)
        drawerToggle?.syncState()

        if (hasUp) {
            drawerToggle?.isDrawerIndicatorEnabled = false
            activityOwner.activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else if (hasHome) {
            activityOwner.activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            drawerToggle?.isDrawerIndicatorEnabled = true
        }

        drawerToggle?.syncState()
    }

    override fun onBackPressed(): Boolean {
        if (view?.isDrawerOpen(GravityCompat.START) ?: false) {
            view?.closeDrawer(GravityCompat.START)
            return true
        } else {
            val content = (view?.findViewById(R.id.containerView) as FrameLayout).getChildAt(0)
            val key = Flow.getKey<Any>(content)
            val component = content?.context!!.getComponent(Any::class)!!
            val presenter = (key as? HasPresenter<*>)?.getPresenter(component)
            return (presenter as? HandlesBack)?.onBackPressed() ?: false
        }
    }
}
