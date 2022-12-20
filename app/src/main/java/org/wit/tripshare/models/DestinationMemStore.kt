package org.wit.tripshare.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class DestinationMemStore : DestinationStore {

    val destinations = ArrayList<DestinationModel>()

    override fun findAll(): List<DestinationModel> {
        return destinations
    }

    override fun create(destination: DestinationModel) {
        destination.id = getId()
        destinations.add(destination)
        logAll()
    }

    override fun update(destination: DestinationModel) {
        val foundDestination: DestinationModel? = destinations.find { p -> p.id == destination.id }
        if (foundDestination != null) {
            foundDestination.title = destination.title
            foundDestination.description = destination.description
            foundDestination.pros = destination.pros
            foundDestination.cons = destination.cons
            foundDestination.dateArrived = destination.dateArrived
            foundDestination.rating = destination.rating
            foundDestination.image = destination.image
            foundDestination.lat = destination.lat
            foundDestination.lng = destination.lng
            foundDestination.zoom = destination.zoom
            logAll()
        }
    }

    override fun delete(destination: DestinationModel) {
        destinations.remove(destination)
        logAll()
    }

    private fun logAll() {
        destinations.forEach { i("$it") }
    }
    override fun findById(id:Long) : DestinationModel? {
        val foundDestination: DestinationModel? = destinations.find { it.id == id }
        return foundDestination
    }
}