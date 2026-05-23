package com.henriquesebastiao.helpos.core.di

import android.content.Context
import com.henriquesebastiao.helpos.core.security.SecurePrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {
    @Provides
    @Singleton
    fun provideSecurePrefs(@ApplicationContext context: Context): SecurePrefs =
        SecurePrefs(context)
}
