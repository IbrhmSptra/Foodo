package id.kotlin.foodo.Resep.recycleMarimasak

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface API_marimasak {
    @GET("/rest/v1/marimasak?select=*&order=tahap.asc")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Query("food") queryfood : String
    ) : Response<List<marimasakAPI>>
}