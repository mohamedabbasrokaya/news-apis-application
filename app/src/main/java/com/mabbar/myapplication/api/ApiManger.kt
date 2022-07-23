package com.mabbar.myapplication.api

import android.accessibilityservice.GestureDescription
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManger {
   companion object{
       private var retrofit:Retrofit?=null
       private fun getInstance():Retrofit{
           if (retrofit==null){
               val interceptor = HttpLoggingInterceptor { message -> Log.e("api", message) }
               interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
               val client= OkHttpClient.Builder()
                   .addInterceptor(interceptor)
                   .build()


              retrofit=Retrofit.Builder()
                  .client(client)
                  .baseUrl("https://newsapi.org")
                  .addConverterFactory(GsonConverterFactory.create())
                  .build()

           }
           return retrofit!!;

       }
       fun getApis():Webservices{
           return getInstance().create(Webservices::class.java)
       }

   }
}