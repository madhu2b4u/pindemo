package com.pindemo.di

import com.pindemo.pin.fragment.PinFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributesPinFragment(): PinFragment

}