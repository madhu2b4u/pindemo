package com.pindemo.di

import android.app.Application
import com.pindemo.PinDemoApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        FragmentBuilderModule::class,
        ActivityBuilderModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<PinDemoApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }

    override fun inject(app: PinDemoApp)
}