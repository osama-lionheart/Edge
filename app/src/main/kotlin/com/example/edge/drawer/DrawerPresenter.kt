package com.example.edge.drawer

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import com.example.edge.R
import com.example.edge.common.ActivityOwner
import com.example.edge.common.HandlesBack
import com.example.edge.common.HasPresenter
import com.example.edge.common.ScopeSingleton
import com.example.edge.gallery.GalleryScreen
import com.example.edge.slideshow.SlideshowScreen
import flow.Direction
import flow.Flow
import flow.History
import mortar.ViewPresenter
import javax.inject.Inject

@ScopeSingleton(DrawerComponent::class)
class DrawerPresenter @Inject constructor(
        private val activityOwner: ActivityOwner) : ViewPresenter<View>(), HandlesBack {
    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onLoad(savedInstanceState: Bundle?) {
        val navigationView = view.findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(onNavigationItemSelected)

        activityOwner.onOptionsItemsSelectedListener = {
            if (drawerToggle?.onOptionsItemSelected(it) ?: false) {
                true
            } else if (it.itemId == android.R.id.home) {
                activityOwner.getActivity()?.onBackPressed()
                true
            }

            false
        }
    }

    val onNavigationItemSelected: (MenuItem) -> Boolean = {
        (view as DrawerLayout).closeDrawer(GravityCompat.START)

        when (it.itemId) {
            R.id.nav_gallery -> Flow.get(view).setHistory(History.single(GalleryScreen()), Direction.REPLACE)
            R.id.nav_slideshow -> Flow.get(view).set(SlideshowScreen())
        }

        true
    }

    fun setToolbar(toolbar: Toolbar, hasHome: Boolean, hasUp: Boolean) {
        activityOwner.getActivity()?.setSupportActionBar(toolbar)

        drawerToggle = ActionBarDrawerToggle(
                activityOwner.getActivity(), (view as DrawerLayout), R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        (view as DrawerLayout).addDrawerListener(drawerToggle!!)
        drawerToggle?.syncState()

        if (hasUp) {
            drawerToggle?.isDrawerIndicatorEnabled = false
            activityOwner.getActivity()?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else if (hasHome) {
            activityOwner.getActivity()?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            drawerToggle?.isDrawerIndicatorEnabled = true
        }

        drawerToggle?.syncState()
    }

    override fun onBackPressed(): Boolean {
        if ((view as DrawerLayout).isDrawerOpen(GravityCompat.START)) {
            (view as DrawerLayout).closeDrawer(GravityCompat.START)
            return true
        } else {
            val content = (view?.findViewById(R.id.containerView) as FrameLayout).getChildAt(0)
            val key = Flow.getKey<Any>(content)
            val presenter = (key as? HasPresenter<*>)?.getPresenter(content?.context!!)
            return (presenter as? HandlesBack)?.onBackPressed() ?: false
        }
    }
}
