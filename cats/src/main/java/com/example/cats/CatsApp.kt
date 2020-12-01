package com.example.cats

import android.app.Application
import com.example.cats.di.AppContainer

class CatsApp : Application() {
    val appContainer by lazy { AppContainer() }
}