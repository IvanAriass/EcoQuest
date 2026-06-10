package com.ecoquest.app.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ecoquest.app.data.room.ComunidadDao
import com.ecoquest.app.data.services.ComunidadEntity
import com.ecoquest.app.data.room.EventoDao
import com.ecoquest.app.data.services.EventoEntity
import com.ecoquest.app.data.room.UsuarioComunidadDao
import com.ecoquest.app.data.room.UsuarioDao
import com.ecoquest.app.data.services.UsuarioComunidadEntity
import com.ecoquest.app.data.room.UsuarioEventoDao
import com.ecoquest.app.data.services.UsuarioEntity
import com.ecoquest.app.data.services.UsuarioEventoEntity


@Database(
    entities = [
        ComunidadEntity::class,
        EventoEntity::class,
        UsuarioEntity::class,
        UsuarioComunidadEntity::class,
        UsuarioEventoEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun comunidadDao(): ComunidadDao
    abstract fun eventoDao(): EventoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun usuarioComunidadDao(): UsuarioComunidadDao
    abstract fun usuarioEventoDao(): UsuarioEventoDao
}