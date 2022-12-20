package org.wit.tripshare.models

import timber.log.Timber.i

var lastRoadtripId = 0L

internal fun getRoadtripId(): Long {
    return lastRoadtripId++
}

class RoadtripMemStore : RoadtripStore {

    val roadtrips = ArrayList<RoadtripModel>()

    override fun findAll(): List<RoadtripModel> {
        return roadtrips
    }

    override fun create(roadtrip: RoadtripModel) {
        roadtrip.id = getRoadtripId()
        roadtrips.add(roadtrip)
        logAll()
    }

    override fun update(roadtrip: RoadtripModel) {
        val foundRoadtrip: RoadtripModel? = roadtrips.find { r -> r.id == roadtrip.id }
        if (foundRoadtrip != null) {
            foundRoadtrip.roadtripTitle = roadtrip.roadtripTitle
            foundRoadtrip.roadtripDescription = roadtrip.roadtripDescription
            foundRoadtrip.roadtripHighlights = roadtrip.roadtripHighlights
            foundRoadtrip.roadtripLowlights = roadtrip.roadtripLowlights
            foundRoadtrip.roadtripDates = roadtrip.roadtripDates
            foundRoadtrip.roadtripRating = roadtrip.roadtripRating
            foundRoadtrip.roadtripImage = roadtrip.roadtripImage
            logAll()
        }
    }

    override fun delete(roadtrip: RoadtripModel) {
        roadtrips.remove(roadtrip)
        logAll()
    }

    private fun logAll() {
        roadtrips.forEach { i("$it") }
    }
}