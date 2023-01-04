package org.wit.tripshare.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object RoadtripManager : RoadtripStore {

    private val roadtrips = ArrayList<RoadtripModel>()

    override fun findAll(): List<RoadtripModel> {
        return roadtrips
    }

    override fun findById(id:Long) : RoadtripModel? {
        val foundRoadtrip: RoadtripModel? = roadtrips.find { it.id == id }
        return foundRoadtrip
    }

    override fun create(roadtrip: RoadtripModel) {
        roadtrip.id = getId()
        roadtrips.add(roadtrip)
        logAll()
    }

    fun logAll() {
        Timber.v("** Roadtrips List **")
        roadtrips.forEach { Timber.v("Roadtrip ${it}") }
    }
}