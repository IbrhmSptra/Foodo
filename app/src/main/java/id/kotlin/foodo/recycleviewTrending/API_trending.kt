package id.kotlin.foodo.recycleviewTrending

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface API_trending {
    @GET("/rest/v1/allfood?id=gte.16&select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String
    ) : Response<List<trendingAPI>>
}