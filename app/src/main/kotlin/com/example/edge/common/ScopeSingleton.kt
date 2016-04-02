package com.example.edge.common

import javax.inject.Scope
import kotlin.reflect.KClass

@Scope
annotation class ScopeSingleton(val value: KClass<*>)
