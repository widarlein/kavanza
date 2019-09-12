package com.github.widarlein.kavanza

import com.github.widarlein.kavanza.model.UserCredentials

object Avanza {
    /**
     * Logins to the Avanza api and returns a client for further interaction with the api.
     *
     * @param userCredentials an object with pertinent information about the user
     * @param totpSecret the secret earlier established in Avanza settings for 2FA
     * @return the Avanza client
     */
    fun connect(userCredentials: UserCredentials, totpSecret: String): AvanzaClient = AvanzaClient.also { it.login(userCredentials, totpSecret) }
}