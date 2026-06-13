package com.ecoquest.app.di

import android.content.Context
import androidx.room.Room
import com.ecoquest.app.data.local.EcoQuestDatabase
import com.ecoquest.app.data.local.dao.ComunidadDao
import com.ecoquest.app.data.local.dao.EventoDao
import com.ecoquest.app.data.local.dao.ProductoDao
import com.ecoquest.app.data.local.dao.RetoDao
import com.ecoquest.app.data.local.dao.TransaccionPuntosDao
import com.ecoquest.app.data.local.dao.UsuarioComunidadDao
import com.ecoquest.app.data.local.dao.UsuarioDao
import com.ecoquest.app.data.local.dao.UsuarioEventoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): EcoQuestDatabase =
        Room.databaseBuilder(context, EcoQuestDatabase::class.java, "ecoquest.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideComunidadDao(db: EcoQuestDatabase): ComunidadDao = db.comunidadDao()

    @Provides
    fun provideEventoDao(db: EcoQuestDatabase): EventoDao = db.eventoDao()

    @Provides
    fun provideUsuarioDao(db: EcoQuestDatabase): UsuarioDao = db.usuarioDao()

    @Provides
    fun provideUsuarioComunidadDao(db: EcoQuestDatabase): UsuarioComunidadDao = db.usuarioComunidadDao()

    @Provides
    fun provideProductoDao(db: EcoQuestDatabase): ProductoDao = db.productoDao()

    @Provides
    fun provideUsuarioEventoDao(db: EcoQuestDatabase): UsuarioEventoDao = db.usuarioEventoDao()

    @Provides
    fun provideRetoDao(db: EcoQuestDatabase): RetoDao = db.retoDao()

    @Provides
    fun provideTransaccionPuntosDao(db: EcoQuestDatabase): TransaccionPuntosDao = db.transaccionPuntosDao()
}
