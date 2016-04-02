package com.example.edge.common

import android.os.Parcelable

import flow.KeyParceler

/**
 * Assumes states are [Parcelable].
 */
internal class BasicKeyParceler : KeyParceler {
    override fun toParcelable(key: Any): Parcelable {
        return key as Parcelable
    }

    override fun toKey(parcelable: Parcelable): Any {
        return parcelable
    }
}
