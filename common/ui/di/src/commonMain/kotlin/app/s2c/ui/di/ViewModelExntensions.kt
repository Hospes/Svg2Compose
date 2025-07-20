package app.s2c.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras

/**
 * Returns a new [CreationExtras] with the original entries plus the passed in creation
 * callback. The callback is used by kotlin-inject to create ViewModels annotated with
 * `ContributesViewModel` and non-SavedStateHandle [Assisted] parameters.
 *
 * @param callback A creation callback that takes an assisted factory and returns a [ViewModel].
 */
fun <VMF> CreationExtras.withCreationCallback(callback: CreationExtras.(VMF) -> ViewModel): CreationExtras =
    MutableCreationExtras(this).addCreationCallback(callback)

/**
 * Returns the [MutableCreationExtras] with the passed in creation callback added. The callback is used by
 * kotlin-inject to create ViewModels annotated with `ContributesViewModel` and non-SavedStateHandle
 * [Assisted] parameters.
 *
 * @param callback A creation callback that takes an assisted factory and returns a [ViewModel].
 */
@Suppress("UNCHECKED_CAST")
fun <VMF> MutableCreationExtras.addCreationCallback(callback: CreationExtras.(VMF) -> ViewModel): CreationExtras =
    this.apply {
        this[MetroViewModelFactory.CREATION_CALLBACK_KEY] = { factory -> callback(factory as VMF) }
    }