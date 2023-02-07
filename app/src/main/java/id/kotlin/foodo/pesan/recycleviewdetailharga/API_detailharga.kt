package id.kotlin.foodo.pesan.recycleviewdetailharga

import id.kotlin.foodo.Resep.recycleviewBahan.bahanAPI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface API_detailharga {
    @GET("/rest/v1/bahandetail?select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Query("food") queryfood : String
    ) : Response<List<detailHargaAPI>>
}