package com.example.edge.common

import android.R
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.example.edge.gallery.GalleryScreen
import flow.Flow
import flow.KeyDispatcher
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var activityOwner: ActivityOwner
    lateinit var component: MainActivityComponent

    override fun attachBaseContext(newBase: Context?) {
        component = getComponent(newBase!!.applicationContext)

        val baseContext = Flow.configure(newBase, this)
                .addServicesFactory(DaggerService(component))
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

        component.inject(this)

        activityOwner.attach(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        activityOwner.detach(this)
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
}
