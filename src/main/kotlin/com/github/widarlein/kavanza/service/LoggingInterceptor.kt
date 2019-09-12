package com.github.widarlein.kavanza.service

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer

object LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val copy = request.newBuilder().build()
        println("======= Sending Request =======")
        println("URL ${copy.url}")
        println("Body ${copy.bodyToString()}")
        println("Headers ${copy.headers}")
        println("======= End Request =======\n")

        val response = chain.proceed(request)
        val responseString = response.body?.string()

        println("======= Got Response =======")
        println("Response was susscess: ${response.isSuccessful} with ${response.code}")
        println("Body $responseString")
        println("Headers ${response.headers}")
        println("======= End Response =======")

        return response.newBuilder()
            .body(responseString?.toResponseBody(response.body?.contentType()))
            .build()
    }
}

fun Request.bodyToString(): String {
    val buffer = Buffer()
    this.body?.writeTo(buffer)
    return buffer.readUtf8()
}