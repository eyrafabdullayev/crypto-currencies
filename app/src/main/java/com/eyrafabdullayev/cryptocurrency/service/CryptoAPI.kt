package com.eyrafabdullayev.cryptocurrency.service

import com.eyrafabdullayev.cryptocurrency.model.CryptoModel
import io.reactivex.Observable
import retrofit2.http.GET

interface CryptoAPI {

    @GET("prices?key=8617ea8dcee6c9e11254e82e20626553")
    fun getData(): Observable<List<CryptoModel>>
//    fun getData(): Call<List<CryptoModel>>
}