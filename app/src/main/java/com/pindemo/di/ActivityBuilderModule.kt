package com.pindemo.di

import com.pindemo.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun contributesMainActivity(): MainActivity

}