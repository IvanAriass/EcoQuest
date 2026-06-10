package com.ecoquest.app.di

import com.ecoquest.app.data.repository.ComunidadRepositoryImpl
import com.ecoquest.app.data.repository.EventoRepositoryImpl
import com.ecoquest.app.data.repository.UsuarioComunidadRepositoryImpl
import com.ecoquest.app.data.repository.UsuarioEventoRepositoryImpl
import com.ecoquest.app.data.repository.UsuarioRepositoryImpl
import com.ecoquest.app.domain.repository.ComunidadRepository
import com.ecoquest.app.domain.repository.EventoRepository
import com.ecoquest.app.domain.repository.UsuarioComunidadRepository
import com.ecoquest.app.domain.repository.UsuarioEventoRepository
import com.ecoquest.app.domain.repository.UsuarioRepository
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
    abstract fun bindUsuarioRepository(impl: UsuarioRepositoryImpl): UsuarioRepository

    @Binds
    @Singleton
    abstract fun bindComunidadRepository(impl: ComunidadRepositoryImpl): ComunidadRepository

    @Binds
    @Singleton
    abstract fun bindEventoRepository(impl: EventoRepositoryImpl): EventoRepository

    @Binds
    @Singleton
    abstract fun bindUsuarioComunidadRepository(impl: UsuarioComunidadRepositoryImpl): UsuarioComunidadRepository

    @Binds
    @Singleton
    abstract fun bindUsuarioEventoRepository(impl: UsuarioEventoRepositoryImpl): UsuarioEventoRepository
}
