package com.example.edge.common

interface Presenter<in T> {
    fun onEnter()
    fun attach(view: T)
    fun detach(view: T)
    fun onExit()
}
