package com.florianschoeberl.hiringfs.di

import com.florianschoeberl.hiringfs.features.start.DetailActivity
import com.florianschoeberl.hiringfs.features.start.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @PerActivity
    @ContributesAndroidInjector
    fun provideMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector
    fun provideDetailActivity(): DetailActivity
}
