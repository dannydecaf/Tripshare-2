package org.wit.tripshare.main

import android.app.Application
import org.wit.tripshare.models.RoadtripJSONStore
import org.wit.tripshare.models.*
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var roadtripsStore: RoadtripStore
    lateinit var destinationsStore: DestinationStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        roadtripsStore = RoadtripJSONStore(applicationContext)
        i("Roadtrip started")
        destinationsStore = DestinationJSONStore(applicationContext)
        i("Destination started")
    }
}