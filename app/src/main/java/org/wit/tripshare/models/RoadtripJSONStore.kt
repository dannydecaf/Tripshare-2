package org.wit.tripshare.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.tripshare.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

const val ROADTRIP_JSON_FILE = "roadtrips.json"
val roadtripGsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, RoadtripUriParser())
    .create()
val roadtripListType: Type = object : TypeToken<ArrayList<RoadtripModel>>() {}.type

fun generateRandomRoadtripId(): Long {
    return Random().nextLong()
}

class RoadtripJSONStore(private val context: Context) : RoadtripStore {

    var roadtrips = mutableListOf<RoadtripModel>()

    init {
        if (exists(context, ROADTRIP_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<RoadtripModel> {
        logAll()
        return roadtrips
    }

    override fun create(roadtrip: RoadtripModel) {
        roadtrip.id = generateRandomRoadtripId()
        roadtrips.add(roadtrip)
        serialize()
    }

    override fun update(roadtrip: RoadtripModel) {
        val roadtripsList = findAll() as ArrayList<RoadtripModel>
        var foundRoadtrip: RoadtripModel? = roadtripsList.find {r -> r.id == roadtrip.id }
        if(foundRoadtrip != null) {
            foundRoadtrip.roadtripTitle = roadtrip.roadtripTitle
            foundRoadtrip.roadtripDescription = roadtrip.roadtripDescription
            foundRoadtrip.roadtripHighlights = roadtrip.roadtripHighlights
            foundRoadtrip.roadtripLowlights = roadtrip.roadtripLowlights
            foundRoadtrip.roadtripDates = roadtrip.roadtripDates
            foundRoadtrip.roadtripRating = roadtrip.roadtripRating
            foundRoadtrip.roadtripImage = roadtrip.roadtripImage
        }
        serialize()
    }

    override fun delete(roadtrip: RoadtripModel) {
        roadtrips.remove(roadtrip)
        serialize()
    }

    private fun serialize() {
        val jsonString = roadtripGsonBuilder.toJson(roadtrips, roadtripListType)
        write(context, ROADTRIP_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, ROADTRIP_JSON_FILE)
        roadtrips = roadtripGsonBuilder.fromJson(jsonString, roadtripListType)
    }

    private fun logAll() {
        roadtrips.forEach { Timber.i("$it") }
    }
}

class RoadtripUriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
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