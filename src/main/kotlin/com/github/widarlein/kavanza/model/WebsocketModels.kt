package com.github.widarlein.kavanza.model

enum class ChannelType {
    QUOTES,
    ORDERDEPTHS,
    TRADES,
    BROKERTRADESUMMARY,
    POSITIONS,
    ORDERS,
    DEALS,
    ACCOUNTS;
}

data class Handshake(
    val channel: String,
    val minimumVersion: String,
    val version: String,
    val advice: Advice,
    val ext: Ext,
    val supportedConnectionTypes: List<String>
) {
    companion object {
        fun fromSubscriptionId(subscriptionId: String) = Handshake(
            "/meta/handshake",
            "1.0",
            "1.0",
            Advice(60000, 0),
            Ext(subscriptionId),
            listOf( "websocket",
                "long-polling",
                "callback-polling")
        )
    }
}

data class HandshakeResponse(
    val channel: String,
    val minimumVersion: String,
    val clientId: String,
    val version: String,
    val advice: Advice,
    val ext: Ext,
    val supportedConnectionTypes: List<String>,
    val successful: Boolean
)

data class Advice(
    val timeout: Int,
    val interval: Int? = null,
    val reconnect: String? = null
)

data class Ext(
    val subscriptionId: String
)
