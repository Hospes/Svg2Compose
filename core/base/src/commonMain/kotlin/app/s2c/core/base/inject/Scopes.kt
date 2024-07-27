package app.s2c.core.base.inject

import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Scope

@Scope
annotation class ApplicationScope

@Scope
annotation class ActivityScope

typealias ApplicationCoroutineScope = CoroutineScope
