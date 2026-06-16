package com.ecoquest.app

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.decode.SvgDecoder
import com.ecoquest.app.data.local.seed.SeedDataInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class EcoQuestApp : Application(), ImageLoaderFactory {

    @Inject
    lateinit var seedDataInitializer: SeedDataInitializer

    override fun onCreate() {
        super.onCreate()
        seedDataInitializer.initialize()
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components { add(SvgDecoder.Factory()) }
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("coil_cache"))
                    .maxSizeBytes(50 * 1024 * 1024)
                    .build()
            }
            .build()
    }
}
