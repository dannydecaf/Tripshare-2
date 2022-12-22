package org.wit.tripshare.views.destination

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.tripshare.databinding.ActivityDestinationBinding
import org.wit.tripshare.helpers.showImagePicker
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.DestinationLocation
import org.wit.tripshare.models.DestinationModel
import org.wit.tripshare.views.location.EditLocationView
import timber.log.Timber

class DestinationPresenter (private val view: DestinationView) {

    var destination = DestinationModel()
    var app: MainApp = view.application as MainApp
    var binding: ActivityDestinationBinding = ActivityDestinationBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false

    init {
        if (view.intent.hasExtra("destination_edit")) {
            edit = true
            destination = view.intent.extras?.getParcelable("destination_edit")!!
            view.showDestination(destination)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(title: String, description: String, pros: String, cons: String, dateArrived: String, rating: Float) {
        destination.title = title
        destination.description = description
        destination.pros = pros
        destination.cons = cons
        destination.dateArrived = dateArrived
        destination.rating = rating
        if (edit) {
            app.destinations.update(destination)
        } else {
            app.destinations.create(destination)
        }

        view.finish()

    }

    fun doCancel() {
        view.finish()

    }

    fun doDelete() {
        app.destinations.delete(destination)
        view.finish()

    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher)
    }

    fun doSetLocation() {
        val location = DestinationLocation(52.245696, -7.139102, 15f)
        if (destination.zoom != 0f) {
            location.lat =  destination.lat
            location.lng = destination.lng
            location.zoom = destination.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }
    fun cacheDestination (title: String, description: String, pros: String, cons: String, dateArrived: String, rating: Float) {
        destination.title = title
        destination.description = description
        destination.pros = pros
        destination.cons = cons
        destination.dateArrived = dateArrived
        destination.rating = rating
    }

    private fun registerImagePickerCallback() {

        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            destination.image = result.data!!.data!!
                            view.updateImage(destination.image)
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }

            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<DestinationLocation>("location")!!
                            Timber.i("Location == $location")
                            destination.lat = location.lat
                            destination.lng = location.lng
                            destination.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }

            }
    }
}
