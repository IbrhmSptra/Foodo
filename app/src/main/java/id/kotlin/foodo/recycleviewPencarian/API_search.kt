package id.kotlin.foodo.recycleviewPencarian

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface API_search {
    @GET("/rest/v1/allfood?select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String
    ) : Response<List<searchAPI>>
}