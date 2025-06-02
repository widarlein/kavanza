package com.github.widarlein.kavanza.service

import okhttp3.Interceptor
import okhttp3.Response

object HeaderInterceptor : Interceptor {

    var authenticationHeaders: AuthenticationHeaders? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("User-Agent", "kavanza client")
            .also {
                val authenticationHeaders = this.authenticationHeaders
                if (authenticationHeaders != null) {
                    it.header("X-SecurityToken", authenticationHeaders.securityToken)
                    it.header("Cookie", authenticationHeaders.csCookie)
                }
            }
            .build()

        return chain.proceed(request)
    }

    data class AuthenticationHeaders(
        val securityToken: String,
        val csid: String,
        val cstoken: String
    ) {
        val csCookie
            get() = "csid=$csid; cstoken=$cstoken;"
    }
}