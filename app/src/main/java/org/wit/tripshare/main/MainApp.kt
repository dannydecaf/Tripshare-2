package org.wit.tripshare.main

import android.app.Application
import org.wit.tripshare.models.RoadtripJSONStore
import org.wit.tripshare.models.*
import org.wit.tripstore.models.DestinationStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var roadtrips: RoadtripStore
    lateinit var destinations: DestinationStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        roadtrips = RoadtripJSONStore(applicationContext)
        i("Roadtrip started")
        destinations = DestinationJSONStore(applicationContext)
        i("Destination started")
    }
}