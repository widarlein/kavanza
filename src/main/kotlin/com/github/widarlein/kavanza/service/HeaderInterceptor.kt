package com.github.widarlein.kavanza.service

import okhttp3.Interceptor
import okhttp3.Response

object HeaderInterceptor : Interceptor {

    var authenticationHeaders: AuthenticationHeaders? = null

    private var _userAgent: String? = null

    private val userAgent: String
        get() = _userAgent ?: "kavanza-client"

    fun startUserAgentSession() {
        _userAgent = userAgents.random()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("User-Agent", userAgent)
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

private val userAgents = listOf(
    // Desktop Browsers (Common)
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36", // Latest Chrome on Windows
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.5 Safari/605.1.15", // Latest Safari on macOS
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:126.0) Gecko/20100101 Firefox/126.0", // Latest Firefox on Windows
    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36", // Latest Chrome on Linux
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Edge/125.0.0.0 Safari/537.36", // Latest Edge on Windows

    // Mobile Browsers
    "Mozilla/5.0 (Linux; Android 10; SM-G973F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Mobile Safari/537.36", // Chrome on Android (Samsung Galaxy S10)
    "Mozilla/5.0 (iPhone; CPU iPhone OS 17_5_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.5 Mobile/15E148 Safari/604.1", // Safari on iOS (iPhone)
    "Mozilla/5.0 (Linux; Android 12; Pixel 6) AppleWebKit/537.36 (KHTML, like Gecko) Firefox/126.0 Mobile Safari/537.36", // Firefox on Android (Google Pixel 6)
    "Mozilla/5.0 (iPad; CPU OS 17_5_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/125.0.0.0 Mobile/15E148 Safari/604.1", // Chrome on iOS (iPad)

    // Older / Less Common Browsers
    "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko", // Internet Explorer 11 on Windows 7
    "Mozilla/5.0 (compatible; Konqueror/4.5; FreeBSD) KHTML/4.5.4 (like Gecko)", // Konqueror (Linux/Unix)
    "Opera/9.80 (Windows NT 6.0) Presto/2.12.388 Version/12.14", // Opera (old Presto engine)

    // Smart TVs / Consoles
    "Mozilla/5.0 (SMART-TV; Linux; Tizen 6.0) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/4.0 Chrome/99.0.4844.78 Safari/537.36", // Samsung Smart TV
    "Mozilla/5.0 (Nintendo Switch; WifiWebBrowser) AppleWebKit/606.1.15 (KHTML, like Gecko) NX/7.0.1.2000.10 Mobile/16B92 Safari/606.1.15", // Nintendo Switch
    "Mozilla/5.0 (PlayStation 4) AppleWebKit/537.73 (KHTML, like Gecko)", // PlayStation 4
    "Mozilla/5.0 (Xbox One) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edge/108.0.1772.0" // Xbox One
)