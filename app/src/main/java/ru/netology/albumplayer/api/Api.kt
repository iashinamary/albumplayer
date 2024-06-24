package ru.netology.albumplayer.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import ru.netology.albumplayer.model.Album

const val BASE_URL =
    "https://raw.githubusercontent.com/netology-code/andad-homeworks/master/09_multimedia/data/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface Api {

    @GET("album.json")
    suspend fun getAll(): Album

    object ApiService {
        val api: Api by lazy {
            retrofit.create()
        }
    }
}