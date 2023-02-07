package id.kotlin.foodo.userAPI

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface API_user {
    @POST("/auth/v1/signup")
    suspend fun signUp(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Body data : dataUser
    ) : Response<ResponseBody>

    @POST("/auth/v1/token?grant_type=password")
    suspend fun signIn(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Body data : dataUser
    ) : Response<ResponseBody>
}