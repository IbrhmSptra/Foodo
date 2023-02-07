package id.kotlin.foodo.recycleviewRekomendasi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface API_rekomendasi {
    @GET("/rest/v1/allfood?id=lte.7&select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String
    ) : Response<List<rekomendasiAPI>>
}