package org.wit.tripshare.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.tripshare.helpers.*
import org.wit.tripstore.models.DestinationStore
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "destinations.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
                 .registerTypeAdapter(Uri::class.java, UriParser())
                 .create()
val listType: Type = object : TypeToken<ArrayList<DestinationModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class DestinationJSONStore(private val context: Context) : DestinationStore {

    var destinations = mutableListOf<DestinationModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserializeDestination()
        }
    }

    override fun findAll(): MutableList<DestinationModel> {
        logAll()
        return destinations
    }

    override fun create(destination: DestinationModel) {
        destination.id = generateRandomId()
        destinations.add(destination)
        serializeDestination()
    }


    override fun update(destination: DestinationModel) {
        val destinationsList = findAll() as ArrayList<DestinationModel>
        var foundDestination: DestinationModel? = destinationsList.find { d -> d.id == destination.id }
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
        }
        serializeDestination()
    }

    override fun delete(destination: DestinationModel) {
        destinations.remove(destination)
        serializeDestination()
    }

    private fun serializeDestination() {
        val jsonString = gsonBuilder.toJson(destinations, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserializeDestination() {
        val jsonString = read(context, JSON_FILE)
        destinations = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        destinations.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}