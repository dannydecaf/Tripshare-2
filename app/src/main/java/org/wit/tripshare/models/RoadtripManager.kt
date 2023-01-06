package org.wit.tripshare.models

import androidx.lifecycle.MutableLiveData
import org.wit.tripshare.api.RoadtripClient
import org.wit.tripshare.api.RoadtripWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object RoadtripManager : RoadtripStore {

    private val roadtrips = ArrayList<RoadtripModel>()

    override fun findAll(roadtripsList: MutableLiveData<List<RoadtripModel>>) {

        val call = RoadtripClient.getApi().getall()

        call.enqueue(object : Callback<List<RoadtripModel>> {
            override fun onResponse(call: Call<List<RoadtripModel>>,
                                    response: Response<List<RoadtripModel>>
            ) {
                roadtripsList.value = response.body() as ArrayList<RoadtripModel>
                Timber.i("Retrofit JSON = ${response.body()}")
            }

            override fun onFailure(call: Call<List<RoadtripModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun findById(id:String) : RoadtripModel? {
        val foundRoadtrip: RoadtripModel? = roadtrips.find { it.uid == id }
        return foundRoadtrip
    }

    override fun create(roadtrip: RoadtripModel) {

        val call = RoadtripClient.getApi().post(roadtrip)

        call.enqueue(object : Callback<RoadtripWrapper> {
            override fun onResponse(call: Call<RoadtripWrapper>,
                                    response: Response<RoadtripWrapper>
            ) {
                val roadtripWrapper = response.body()
                if (roadtripWrapper != null) {
                    Timber.i("Retrofit ${roadtripWrapper.message}")
                    Timber.i("Retrofit ${roadtripWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<RoadtripWrapper>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun delete(id: String) {
        val call = RoadtripClient.getApi().delete(id)

        call.enqueue(object : Callback<RoadtripWrapper> {
            override fun onResponse(call: Call<RoadtripWrapper>,
                                    response: Response<RoadtripWrapper>
            ) {
                val roadtripWrapper = response.body()
                if (roadtripWrapper != null) {
                    Timber.i("Retrofit Delete ${roadtripWrapper.message}")
                    Timber.i("Retrofit Delete ${roadtripWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<RoadtripWrapper>, t: Throwable) {
                Timber.i("Retrofit Delete Error : $t.message")
            }
        })
    }

    fun logAll() {
        Timber.v("** Roadtrips List **")
        roadtrips.forEach { Timber.v("Roadtrip ${it}") }
    }
}