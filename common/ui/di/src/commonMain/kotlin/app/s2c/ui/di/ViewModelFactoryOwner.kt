package app.s2c.ui.di

import androidx.lifecycle.ViewModelProvider

interface ViewModelFactoryOwner {
    
    val viewModelFactory: ViewModelProvider.Factory
}