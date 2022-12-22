package org.wit.tripshare.views.roadtriplist

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.RoadtripModel
import org.wit.tripshare.views.destinationlist.DestinationListView
import org.wit.tripshare.views.roadtrip.RoadtripView

class RoadtripListPresenter(val view: RoadtripListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    init {
        app = view.application as MainApp
        registerRefreshCallback()
    }

    fun getRoadtrips() = app.roadtrips.findAll()

    fun doAddRoadtrip() {
        val launcherIntent = Intent(view, RoadtripView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditRoadtrip(roadtrip: RoadtripModel) {
        val launcherIntent = Intent(view, DestinationListView::class.java)
        launcherIntent.putExtra("roadtrip_edit", roadtrip)
    }


    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { getRoadtrips() }
    }
}