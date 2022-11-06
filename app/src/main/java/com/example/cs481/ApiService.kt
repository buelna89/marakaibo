package com.example.cs481

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/todos")
    fun getTodos():Call<MutableList<PostModel>>
}