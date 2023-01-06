package org.wit.tripshare.api

import org.wit.tripshare.models.RoadtripModel
import retrofit2.Call
import retrofit2.http.*


interface RoadtripService {
    @GET("/roadtrips")
    fun getall(): Call<List<RoadtripModel>>

    @GET("/roadtrips/{id}")
    fun get(@Path("id") id: String): Call<RoadtripModel>

    @DELETE("/roadtrips/{id}")
    fun delete(@Path("id") id: String): Call<RoadtripWrapper>

    @POST("/roadtrips")
    fun post(@Body roadtrip: RoadtripModel): Call<RoadtripWrapper>

    @PUT("/roadtrips/{id}")
    fun put(@Path("id") id: String,
            @Body donation: RoadtripModel
    ): Call<RoadtripWrapper>
}