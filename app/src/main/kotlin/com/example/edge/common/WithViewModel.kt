package com.example.edge.common

import kotlin.reflect.KClass

@Retention()
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class WithViewModel(val value: KClass<*>)
