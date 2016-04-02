package com.example.edge.common

import kotlin.reflect.KClass

@Retention()
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class WithComponent(val value: KClass<*>)
