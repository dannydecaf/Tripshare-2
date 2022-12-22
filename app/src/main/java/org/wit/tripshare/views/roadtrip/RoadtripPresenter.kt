package org.wit.tripshare.views.roadtrip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.tripshare.databinding.ActivityRoadtripBinding
import org.wit.tripshare.helpers.showImagePicker
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.RoadtripModel
import timber.log.Timber

class RoadtripPresenter (private val view: RoadtripView) {

    var roadtrip = RoadtripModel()
    var app: MainApp = view.application as MainApp
    var binding: ActivityRoadtripBinding = ActivityRoadtripBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false

    init {
        if (view.intent.hasExtra("roadtrip_edit")) {
            edit = true
            roadtrip = view.intent.extras?.getParcelable("roadtrip_edit")!!
            view.showRoadtrip(roadtrip)
        }
        registerImagePickerCallback()
    }

    fun doAddOrSave(roadtripTitle: String, roadtripDescription: String, roadtripHighlights: String, roadtripLowlights: String, roadtripDates: String, roadtripRating: Float) {
        roadtrip.roadtripTitle = roadtripTitle
        roadtrip.roadtripDescription = roadtripDescription
        roadtrip.roadtripHighlights = roadtripHighlights
        roadtrip.roadtripLowlights = roadtripLowlights
        roadtrip.roadtripDates = roadtripDates
        roadtrip.roadtripRating = roadtripRating
        if (edit) {
            app.roadtrips.update(roadtrip)
        } else {
            app.roadtrips.create(roadtrip)
        }

        view.finish()

    }

    fun doCancel() {
        view.finish()

    }

    fun doDelete() {
        app.roadtrips.delete(roadtrip)
        view.finish()

    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher)
    }

    fun cacheRoadtrip (roadtripTitle: String, roadtripDescription: String, roadtripHighlights: String, roadtripLowlights: String, roadtripDates: String, roadtripRating: Float) {
        roadtrip.roadtripTitle = roadtripTitle
        roadtrip.roadtripDescription = roadtripDescription
        roadtrip.roadtripHighlights = roadtripHighlights
        roadtrip.roadtripLowlights = roadtripLowlights
        roadtrip.roadtripDates = roadtripDates
        roadtrip.roadtripRating = roadtripRating
    }

    private fun registerImagePickerCallback() {

        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            roadtrip.roadtripImage = result.data!!.data!!
                            view.updateImage(roadtrip.roadtripImage)
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }

            }
    }
}