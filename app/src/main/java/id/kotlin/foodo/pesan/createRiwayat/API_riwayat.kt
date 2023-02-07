package id.kotlin.foodo.pesan.createRiwayat

import id.kotlin.foodo.Resep.bookmark.createbookmark
import retrofit2.http.*

interface API_riwayat {
    @POST("/rest/v1/riwayat")
    suspend fun createriwayat(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Body data : dataRiwayat
    )
}