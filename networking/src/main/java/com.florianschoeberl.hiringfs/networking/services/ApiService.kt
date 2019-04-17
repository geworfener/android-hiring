package com.florianschoeberl.hiringfs.networking.services

import com.florianschoeberl.hiringfs.networking.model.AuthToken
import com.florianschoeberl.hiringfs.networking.model.Club
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

const val API_VERSION = "v1"

interface ApiService {

    @POST("/$API_VERSION/login")
    fun login(): Single<AuthToken>

    @GET("clubs.json")
    fun getClubs(): Call<List<Club>>
}
