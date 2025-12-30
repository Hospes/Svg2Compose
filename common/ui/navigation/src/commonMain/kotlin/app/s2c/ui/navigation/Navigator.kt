package app.s2c.ui.navigation

import androidx.navigation3.runtime.NavKey

interface Navigator {
    fun navigateUp()

    fun navigateTo(key: NavKey)
}