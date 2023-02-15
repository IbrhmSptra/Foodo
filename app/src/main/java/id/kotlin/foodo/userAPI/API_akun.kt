package id.kotlin.foodo.userAPI

import retrofit2.Response
import retrofit2.http.*

interface API_akun {

    @GET("/rest/v1/akun?select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Query("email") query: String
    ) : Response<List<getAkun>>

    @POST("/rest/v1/akun")
    suspend fun createakun(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Body data : dataRegAkun
    )


}