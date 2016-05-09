package com.example.edge.common

import android.R
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.ViewGroup
import com.example.edge.gallery.GalleryScreen
import flow.Flow
import flow.KeyDispatcher
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var activityOwner: ActivityOwner
    lateinit var component: MainActivityComponent

    override fun attachBaseContext(newBase: Context?) {
        component = getComponent(newBase!!.applicationContext)

        val baseContext = Flow.configure(newBase, this)
                .addServicesFactory(DaggerService(component))
                //.addServicesFactory(MortarService(MortarScope.getScope(newBase!!.applicationContext)))
                .dispatcher(KeyDispatcher.configure(this, ActivityKeyChanger(this)).build())
                .defaultKey(GalleryScreen())
                .keyParceler(BasicKeyParceler())
                .install()

        super.attachBaseContext(baseContext)
    }

    fun getComponent(context: Context): MainActivityComponent {
        @Suppress("UNCHECKED_CAST")
        val services = context.getSystemService(DaggerService.SERVICE_NAME) as MutableMap<String, Any>

        val componentName = MainActivityComponent::class.simpleName!!
        val component = services[componentName] as? MainActivityComponent ?: DaggerMainActivityComponent.create()
        services.put(componentName, component)

        return component;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState)

        component.inject(this)

        activityOwner.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        activityOwner.dropView(this)

        /*if (isFinishing) {
            val activityScope = MortarScope.findChild(applicationContext, getScopeName())
            activityScope?.destroy()
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (activityOwner.onOptionsItemSelected(item!!)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val view = findViewById(R.id.content) as ViewGroup
        val content = view.getChildAt(0)
        val key = Flow.getKey<Any>(content)
        val presenter = (key as? HasPresenter<*>)?.getPresenter(content.context)
        val backHandled = (presenter as? HandlesBack)?.onBackPressed() ?: false

        if (!backHandled && !Flow.get(this).goBack()) {
            super.onBackPressed()
        }
    }

    override fun getSystemService(name: String): Any {
        var activityScope = getScope(applicationContext)

        if (activityScope.hasService(name)) {
            return activityScope.getService<Any>(name)
        }

        return super.getSystemService(name)
    }

    private fun getScope(context: Context): MortarScope {
        val parent = MortarScope.getScope(context)
        var child: MortarScope? = MortarScope.findChild(context, getScopeName())

        if (child == null) {
            child = parent.buildChild()
                    .withService(BundleServiceRunner.SERVICE_NAME, BundleServiceRunner())
                    .withService(DaggerService.SERVICE_NAME, component)
                    .build(getScopeName())
        }

        return child as MortarScope
    }

    private fun getScopeName(): String {
        return javaClass.simpleName;
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState)
    }
}
