package com.example.edge

import android.os.Parcelable

import flow.KeyParceler

/**
 * Assumes states are [Parcelable].
 *
 *
 * A more realistic implementation might rely on a library like auto-value-parcel,
 * auto-parcel, or parceler.
 */
internal class BasicKeyParceler : KeyParceler {
    override fun toParcelable(key: Any): Parcelable {
        return key as Parcelable
    }

    override fun toKey(parcelable: Parcelable): Any {
        return parcelable
    }
}
