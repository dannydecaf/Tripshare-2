package org.wit.tripshare.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import org.wit.tripshare.api.RoadtripClient
import org.wit.tripshare.api.RoadtripWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object RoadtripManager : RoadtripStore {

    private val roadtrips = ArrayList<RoadtripModel>()

    override fun findAll(roadtripsList: MutableLiveData<List<RoadtripModel>>) {

        val call = RoadtripClient.getApi().findall()

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

    override fun findAll(email: String, roadtripsList: MutableLiveData<List<RoadtripModel>>) {

        val call = RoadtripClient.getApi().findall(email)

        call.enqueue(object : Callback<List<RoadtripModel>> {
            override fun onResponse(call: Call<List<RoadtripModel>>,
                                    response: Response<List<RoadtripModel>>
            ) {
                roadtripsList.value = response.body() as ArrayList<RoadtripModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<RoadtripModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findById(email: String, id: String, roadtrip: MutableLiveData<RoadtripModel>)   {

        val call = RoadtripClient.getApi().get(email,id)

        call.enqueue(object : Callback<RoadtripModel> {
            override fun onResponse(call: Call<RoadtripModel>, response: Response<RoadtripModel>) {
                roadtrip.value = response.body() as RoadtripModel
                Timber.i("Retrofit findById() = ${response.body()}")
            }

            override fun onFailure(call: Call<RoadtripModel>, t: Throwable) {
                Timber.i("Retrofit findById() Error : $t.message")
            }
        })
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, roadtrip: RoadtripModel) {

        val call = RoadtripClient.getApi().post(roadtrip.email,roadtrip)

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

    override fun delete(email: String,id: String) {

        val call = RoadtripClient.getApi().delete(email,id)

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

            override fun update(email: String,id: String, roadtrip: RoadtripModel) {

                val call = RoadtripClient.getApi().put(email,id,roadtrip)

                call.enqueue(object : Callback<RoadtripWrapper> {
                    override fun onResponse(
                        call: Call<RoadtripWrapper>,
                        response: Response<RoadtripWrapper>
                    ) {
                        val donationWrapper = response.body()
                        if (donationWrapper != null) {
                            Timber.i("Retrofit Update ${donationWrapper.message}")
                            Timber.i("Retrofit Update ${donationWrapper.data.toString()}")
                        }
                    }

                    override fun onFailure(call: Call<RoadtripWrapper>, t: Throwable) {
                        Timber.i("Retrofit Update Error : $t.message")
                    }
                })
            }
}