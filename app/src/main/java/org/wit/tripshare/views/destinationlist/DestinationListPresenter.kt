package org.wit.tripshare.views.destinationlist

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.tripshare.activities.DestinationMapsActivity
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.DestinationModel
import org.wit.tripshare.views.destination.DestinationView

class DestinationListPresenter(val view: DestinationListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getDestinations() = app.destinations.findAll()

    fun doAddDestination() {
        val launcherIntent = Intent(view, DestinationView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditDestination(destination: DestinationModel) {
        val launcherIntent = Intent(view, DestinationView::class.java)
        launcherIntent.putExtra("destination_edit", destination)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun doShowDestinationsMap() {
        val launcherIntent = Intent(view, DestinationMapsActivity::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }
    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { getDestinations() }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}
