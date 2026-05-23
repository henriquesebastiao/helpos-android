package com.henriquesebastiao.helpos.core.di

import com.henriquesebastiao.helpos.data.repository.AuthRepositoryImpl
import com.henriquesebastiao.helpos.data.repository.ClientRepositoryImpl
import com.henriquesebastiao.helpos.data.repository.SettingsRepositoryImpl
import com.henriquesebastiao.helpos.domain.repository.AuthRepository
import com.henriquesebastiao.helpos.domain.repository.ClientRepository
import com.henriquesebastiao.helpos.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindClientRepository(impl: ClientRepositoryImpl): ClientRepository
}
