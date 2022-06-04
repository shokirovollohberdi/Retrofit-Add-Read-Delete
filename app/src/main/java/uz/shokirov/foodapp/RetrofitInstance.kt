package uz.shokirov.foodapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.shokirov.foodapp.Constants.BASE_URL

object RetrofitInstance {
    val retrofit:PostApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApi::class.java)
    }
}