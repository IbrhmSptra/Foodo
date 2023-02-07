package id.kotlin.foodo.Resep.bookmark

import retrofit2.Response
import retrofit2.http.*

interface API_detailbookmark {
    @GET("/rest/v1/bookmark?select=food")
    suspend fun getfoodbookmark(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
    ) : Response<List<datafood>>

    @POST("/rest/v1/bookmark")
    suspend fun createbookmark(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Body data : createbookmark
    )

    @DELETE("/rest/v1/bookmark")
    suspend fun deletebookmark(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Query("food") foodbookmark : String
    ) : Response<Unit>
}