package org.wit.tripshare.main

import android.app.Application
import timber.log.Timber

class MainApp : Application() {

    //lateinit var donationsStore: RoadtripStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //  donationsStore = RoadtripManager()
        Timber.i("tripShare Application Started")
    }
}