package org.wit.tripshare.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.tripshare.firebase.FirebaseDBManager
import org.wit.tripshare.models.RoadtripModel
import timber.log.Timber

class RoadtripDetailViewModel : ViewModel() {
    private val roadtrip = MutableLiveData<RoadtripModel>()

    var observableRoadtrip: LiveData<RoadtripModel>
        get() = roadtrip
        set(value) {
            roadtrip.value = value.value
        }

    fun getRoadtrip(userid: String, id: String) {
        try {
            FirebaseDBManager.findById(userid, id, roadtrip)
            Timber.i(
                "Detail getRoadtrip() Success : ${
                    roadtrip.value.toString()
                }"
            )
        } catch (e: Exception) {
            Timber.i("Detail getRoadtrip() Error : $e.message")
        }
    }

    fun updateRoadtrip(userid: String, id: String, roadtrip: RoadtripModel) {
        try {
            FirebaseDBManager.update(userid, id, roadtrip)
            Timber.i("Detail update() Success : $roadtrip")
        } catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}