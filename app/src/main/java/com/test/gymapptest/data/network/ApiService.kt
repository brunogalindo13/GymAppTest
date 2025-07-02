package com.test.gymapptest.data.network

import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    @GET("Rutina/")
    suspend fun getRutinas(): Response<List<RutinaPojoItem>>

}