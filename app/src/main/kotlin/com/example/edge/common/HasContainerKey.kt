package com.example.edge.common

import flow.ClassKey
import flow.MultiKey
import nz.bradcampbell.paperparcel.PaperParcelable

open class HasContainerKey(val containerKey: Any) : ClassKey(), MultiKey, PaperParcelable {
    override fun getKeys(): List<Any> {
        return listOf(containerKey)
    }
}
