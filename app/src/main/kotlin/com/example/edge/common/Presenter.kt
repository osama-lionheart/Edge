package com.example.edge.common

interface Presenter<T> {
    fun attach(view: T)
    fun detach(view: T)
}
