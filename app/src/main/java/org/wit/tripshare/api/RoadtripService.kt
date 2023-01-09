package org.wit.tripshare.api

import org.wit.tripshare.models.RoadtripModel
import retrofit2.Call
import retrofit2.http.*


interface RoadtripService {
    @GET("/roadtrips")
    fun findall(): Call<List<RoadtripModel>>

    @GET("/roadtrips/{email}")
    fun findall(@Path("email") email: String?)
            : Call<List<RoadtripModel>>

    @GET("/roadtrips/{email}/{id}")
    fun get(@Path("email") email: String?,
            @Path("id") id: String): Call<RoadtripModel>

    @DELETE("/roadtrips/{email}/{id}")
    fun delete(@Path("email") email: String?,
               @Path("id") id: String): Call<RoadtripWrapper>

    @POST("/roadtrips/{email}")
    fun post(@Path("email") email: String?,
             @Body roadtrip: RoadtripModel)
            : Call<RoadtripWrapper>

    @PUT("/roadtrips/{email}/{id}")
    fun put(@Path("email") email: String?,
            @Path("id") id: String,
            @Body roadtrip: RoadtripModel
    ): Call<RoadtripWrapper>
}