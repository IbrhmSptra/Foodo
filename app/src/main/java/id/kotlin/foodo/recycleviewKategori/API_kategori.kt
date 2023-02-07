package id.kotlin.foodo.recycleviewKategori

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface API_kategori {
    @GET("/rest/v1/kategori?select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String
    ) : Response<List<kategoriAPI>>
}