package org.wit.tripshare.models

interface RoadtripStore {
    fun findAll(): List<RoadtripModel>
    fun create(roadtrip: RoadtripModel)
    fun update(roadtrip: RoadtripModel)
    fun delete(roadtrip: RoadtripModel)
}