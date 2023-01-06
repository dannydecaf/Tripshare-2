package org.wit.tripshare.models

import androidx.lifecycle.MutableLiveData

interface RoadtripStore {
    fun findAll(roadtripsList: MutableLiveData<List<RoadtripModel>>)
    fun findById(id: String) : RoadtripModel?
    fun create(roadtrip: RoadtripModel)
    fun delete(id: String)
}