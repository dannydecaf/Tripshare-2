package org.wit.tripshare.ui.roadtrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.tripshare.models.RoadtripManager
import org.wit.tripshare.models.RoadtripModel

class RoadtripViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addRoadtrip(roadtrip: RoadtripModel) {
        status.value = try {
            RoadtripManager.create(roadtrip)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}