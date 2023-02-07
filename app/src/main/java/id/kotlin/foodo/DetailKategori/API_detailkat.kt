package id.kotlin.foodo.DetailKategori

import id.kotlin.foodo.recycleviewPencarian.searchAPI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface API_detailkat {
    @GET("/rest/v1/allfood?select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Query("kategori") kategori : String
    ) : Response<List<detailkatAPI>>
}