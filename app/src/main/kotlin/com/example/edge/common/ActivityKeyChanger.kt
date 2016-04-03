package com.example.edge.common

import android.app.Activity
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edge.R
import flow.Direction
import flow.KeyChanger
import flow.State
import flow.TraversalCallback

internal class ActivityKeyChanger(private val activity: Activity) : KeyChanger() {
    override fun changeKey(outgoingState: State?, incomingState: State?, direction: Direction?,
                           incomingContexts: MutableMap<Any, Context>?, callback: TraversalCallback?) {
        val inKey = incomingState?.getKey<Any>()!!
        val outKey = outgoingState?.getKey<Any>()
        val content = activity.findViewById(android.R.id.content) as ViewGroup
        val outgoingView = content.getChildAt(0)

        outgoingState?.save(outgoingView)

        val incomingView: View

        if (inKey !is HasContainerKey || outKey !is HasContainerKey) {
            val mainKey = (inKey as? HasContainerKey)?.containerKey ?: inKey
            val mainContext = incomingContexts?.get(mainKey)!!
            incomingView = inflateViewForKey(mainKey, mainContext)
            activity.setContentView(incomingView)
        } else {
            incomingView = outgoingView
        }

        if (inKey is HasContainerKey) {
            val contentContext = incomingContexts?.get(inKey)!!
            val contentView = inflateViewForKey(inKey, contentContext)

            val frameContainer = incomingView.findViewById(R.id.containerView) as ViewGroup
            frameContainer.removeAllViews()
            frameContainer.addView(contentView)
        }

        incomingState?.restore(incomingView)

        callback?.onTraversalCompleted()
    }

    fun inflateViewForKey(key: Any, context: Context): View {
        val layout = key.javaClass.getAnnotation(Layout::class.java).value
        val view = LayoutInflater.from(context).inflate(layout, null)

        if (key is HasPresenter<*>) {
            val presenter = key.getPresenter(context)

            view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(view: View) {
                    setupDataBinding(view)
                    presenter.attach(view)
                }

                override fun onViewDetachedFromWindow(view: View) {
                    presenter.detach(view);
                }
            })
        } else {
            setupDataBinding(view)
        }

        return view
    }

    fun setupDataBinding(view: View) {
        val binding = DataBindingUtil.bind<ViewDataBinding>(view)
        binding.executePendingBindings()
    }
}
