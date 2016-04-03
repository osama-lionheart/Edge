package com.example.edge.common

interface Presenter<in T> {
    fun attach(view: T)
    fun detach(view: T)
}
