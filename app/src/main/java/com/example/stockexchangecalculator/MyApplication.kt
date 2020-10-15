package com.example.stockexchangecalculator

import android.app.Application

import io.realm.Realm

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}
