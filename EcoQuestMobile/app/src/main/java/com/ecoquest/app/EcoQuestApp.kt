package com.ecoquest.app

import android.app.Application
import com.ecoquest.app.data.local.seed.SeedDataInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class EcoQuestApp : Application() {

    @Inject
    lateinit var seedDataInitializer: SeedDataInitializer

    override fun onCreate() {
        super.onCreate()
        seedDataInitializer.initialize()
    }
}
