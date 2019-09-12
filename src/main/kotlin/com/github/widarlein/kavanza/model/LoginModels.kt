package com.github.widarlein.kavanza.model

data class UserCredentials(val username: String,
                           val password: String,
                           val maxInactiveMinutes: Int = 30)

data class LoginResponse(val twoFactorLogin: TwoFactorLogin)

data class TwoFactorLogin(val transactionId: String,
                          val method: String)

data class TotpRequest(val method: String = "TOTP", val totpCode: String)

class TotpResponse(val authenticationSession: String,
                   val pushSubscriptionId: String,
                   val registrationComplete: Boolean,
                   val customerId: String)

