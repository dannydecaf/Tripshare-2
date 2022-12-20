package org.wit.tripstore.models

import org.wit.tripshare.models.DestinationModel

interface DestinationStore {
    fun findAll(): List<DestinationModel>
    fun create(destination: DestinationModel)
    fun update(destination: DestinationModel)
    fun delete(destination: DestinationModel)
}
