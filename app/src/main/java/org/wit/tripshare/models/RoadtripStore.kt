package org.wit.tripshare.models

interface RoadtripStore {
    fun findAll() : List<RoadtripModel>
    fun findById(id: Long) : RoadtripModel?
    fun create(roadtrip: RoadtripModel)
}