package org.wit.tripshare.models

interface DestinationStore {
    fun findAll(): List<DestinationModel>
    fun create(destination: DestinationModel)
    fun update(destination: DestinationModel)
    fun findById(id:Long) : DestinationModel?
    fun delete(destination: DestinationModel)
}