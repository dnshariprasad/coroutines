package com.hari.coroutines

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
    @GET("/json")
    fun getIpInfo(): Deferred<Response<IpApiResponse>>
}
