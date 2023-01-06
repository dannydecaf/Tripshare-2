package org.wit.tripshare.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.tripshare.models.RoadtripManager
import org.wit.tripshare.models.RoadtripModel
import timber.log.Timber

class RoadtripDetailViewModel : ViewModel() {
    private val roadtrip = MutableLiveData<RoadtripModel>()

    val observableRoadtrip: LiveData<RoadtripModel>
        get() = roadtrip

    fun getRoadtrip(id: String) {
        roadtrip.value = RoadtripManager.findById(id)
    }
}