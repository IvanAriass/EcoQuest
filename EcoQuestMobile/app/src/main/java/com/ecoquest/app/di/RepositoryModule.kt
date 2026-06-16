package com.ecoquest.app.di

import com.ecoquest.app.data.repository.ComentarioRepositoryImpl
import com.ecoquest.app.data.repository.ComunidadRepositoryImpl
import com.ecoquest.app.data.repository.EventoRepositoryImpl
import com.ecoquest.app.data.repository.MensajeRepositoryImpl
import com.ecoquest.app.data.repository.ProductoRepositoryImpl
import com.ecoquest.app.data.repository.RetoRepositoryImpl
import com.ecoquest.app.data.repository.RolRepositoryImpl
import com.ecoquest.app.data.repository.TransaccionPuntosRepositoryImpl
import com.ecoquest.app.data.repository.UsuarioComunidadRepositoryImpl
import com.ecoquest.app.data.repository.UsuarioCosmeticoRepositoryImpl
import com.ecoquest.app.data.repository.UsuarioEventoRepositoryImpl
import com.ecoquest.app.data.repository.UsuarioRepositoryImpl
import com.ecoquest.app.domain.repository.ComentarioRepository
import com.ecoquest.app.domain.repository.ComunidadRepository
import com.ecoquest.app.domain.repository.EventoRepository
import com.ecoquest.app.domain.repository.MensajeRepository
import com.ecoquest.app.domain.repository.ProductoRepository
import com.ecoquest.app.domain.repository.RetoRepository
import com.ecoquest.app.domain.repository.RolRepository
import com.ecoquest.app.domain.repository.TransaccionPuntosRepository
import com.ecoquest.app.domain.repository.UsuarioComunidadRepository
import com.ecoquest.app.domain.repository.UsuarioCosmeticoRepository
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
    abstract fun bindComentarioRepository(impl: ComentarioRepositoryImpl): ComentarioRepository

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

    @Binds
    @Singleton
    abstract fun bindProductoRepository(impl: ProductoRepositoryImpl): ProductoRepository

    @Binds
    @Singleton
    abstract fun bindRetoRepository(impl: RetoRepositoryImpl): RetoRepository

    @Binds
    @Singleton
    abstract fun bindTransaccionPuntosRepository(impl: TransaccionPuntosRepositoryImpl): TransaccionPuntosRepository

    @Binds
    @Singleton
    abstract fun bindUsuarioCosmeticoRepository(impl: UsuarioCosmeticoRepositoryImpl): UsuarioCosmeticoRepository

    @Binds
    @Singleton
    abstract fun bindMensajeRepository(impl: MensajeRepositoryImpl): MensajeRepository

    @Binds
    @Singleton
    abstract fun bindRolRepository(impl: RolRepositoryImpl): RolRepository
}
