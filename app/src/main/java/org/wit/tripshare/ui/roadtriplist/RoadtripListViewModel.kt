package org.wit.tripshare.ui.roadtriplist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.tripshare.models.RoadtripManager
import org.wit.tripshare.models.RoadtripModel

class RoadtripListViewModel : ViewModel() {

    private val roadtripsList = MutableLiveData<List<RoadtripModel>>()

    val observableRoadtripsList: LiveData<List<RoadtripModel>>
        get() = roadtripsList

    init {
        load()
    }

    fun load() {
        roadtripsList.value = RoadtripManager.findAll()
    }
}