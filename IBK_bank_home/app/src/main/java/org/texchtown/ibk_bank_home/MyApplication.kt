package org.texchtown.ibk_bank_home

import android.app.Application


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ListeningToActivityCallbacks())
    }
}