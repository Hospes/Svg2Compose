package app.s2c.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras

/**
 * Returns a new {@code CreationExtras} with the original entries plus the passed in creation
 * callback. The callback is used by Hilt to create {@link AssistedInject}-annotated {@link
 * HiltViewModel}s.
 *
 * @param callback A creation callback that takes an assisted factory and returns a {@code
 *   ViewModel}.
 */
fun <VMF> CreationExtras.withCreationCallback(callback: (VMF) -> ViewModel): CreationExtras =
    MutableCreationExtras(this).addCreationCallback(callback)

/**
 * Returns the {@code MutableCreationExtras} with the passed in creation callback added. The
 * callback is used by Hilt to create {@link AssistedInject}-annotated {@link HiltViewModel}s.
 *
 * @param callback A creation callback that takes an assisted factory and returns a {@code
 *   ViewModel}.
 */
@Suppress("UNCHECKED_CAST")
fun <VMF> MutableCreationExtras.addCreationCallback(callback: (VMF) -> ViewModel): CreationExtras =
    this.apply {
        this[MetroViewModelFactory.CREATION_CALLBACK_KEY] = { factory -> callback(factory as VMF) }
    }