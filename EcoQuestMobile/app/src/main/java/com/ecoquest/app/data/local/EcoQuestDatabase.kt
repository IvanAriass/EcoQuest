package com.ecoquest.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ecoquest.app.data.local.dao.ComunidadDao
import com.ecoquest.app.data.local.dao.EventoDao
import com.ecoquest.app.data.local.dao.ProductoDao
import com.ecoquest.app.data.local.dao.UsuarioComunidadDao
import com.ecoquest.app.data.local.dao.UsuarioDao
import com.ecoquest.app.data.local.dao.UsuarioEventoDao
import com.ecoquest.app.data.local.entity.ComunidadEntity
import com.ecoquest.app.data.local.entity.EventoEntity
import com.ecoquest.app.data.local.entity.ProductoEntity
import com.ecoquest.app.data.local.entity.UsuarioComunidadEntity
import com.ecoquest.app.data.local.entity.UsuarioEntity
import com.ecoquest.app.data.local.entity.UsuarioEventoEntity

@Database(
    entities = [
        ComunidadEntity::class,
        EventoEntity::class,
        ProductoEntity::class,
        UsuarioEntity::class,
        UsuarioComunidadEntity::class,
        UsuarioEventoEntity::class
    ],
    version = 6,
    exportSchema = false
)
abstract class EcoQuestDatabase : RoomDatabase() {
    abstract fun comunidadDao(): ComunidadDao
    abstract fun eventoDao(): EventoDao
    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun usuarioComunidadDao(): UsuarioComunidadDao
    abstract fun usuarioEventoDao(): UsuarioEventoDao
}
