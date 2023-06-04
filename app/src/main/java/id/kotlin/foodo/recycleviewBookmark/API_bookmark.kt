package id.kotlin.foodo.recycleviewBookmark

import id.kotlin.foodo.DetailKategori.detailkatAPI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface API_bookmark {
    @GET("/rest/v1/bookmark?select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Query("id_user") id_user : String
    ) : Response<List<bookmarkAPI>>
}