/*
 * Copyright 2016 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.edge

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import flow.Direction
import flow.KeyChanger
import flow.State
import flow.TraversalCallback

internal class BasicKeyChanger(private val activity: Activity) : KeyChanger() {
    override fun changeKey(outgoingState: State?, incomingState: State?, direction: Direction?,
                           incomingContexts: MutableMap<Any, Context>?, callback: TraversalCallback?) {
        val key = incomingState?.getKey<Any>()
        val context = incomingContexts?.get(key)
        val frame = activity.findViewById(R.id.screenContainer) as ViewGroup

        outgoingState?.save(frame.getChildAt(0))
        frame.removeAllViews()

        val layout = (key as Any).javaClass.getAnnotation(Layout::class.java).value

        val incomingView = LayoutInflater.from(context)
                .inflate(layout, null)

        frame.addView(incomingView)
        incomingState?.restore(incomingView)
        callback?.onTraversalCompleted()
    }
}
