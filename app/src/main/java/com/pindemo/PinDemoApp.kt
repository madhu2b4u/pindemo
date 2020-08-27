package com.pindemo

import com.pindemo.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class PinDemoApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }


}