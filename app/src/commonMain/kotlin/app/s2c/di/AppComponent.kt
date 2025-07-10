package app.s2c.di

import androidx.lifecycle.ViewModelProvider
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ForScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
interface AppComponent {

    @ForScope(AppScope::class)
    val vmFactory: ViewModelProvider.Factory
}