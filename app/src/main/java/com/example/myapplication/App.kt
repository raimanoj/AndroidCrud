package com.example.myapplication

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        private lateinit var app: App

        fun getInstance(): App {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    fun getContext(): Context {
        return applicationContext
    }

}