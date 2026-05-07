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
    fun connect(userCredentials: UserCredentials, totpSecret: String, debugPrintouts: Boolean = false): AvanzaClient =
        AvanzaClient(debugPrintouts).also { it.login(userCredentials, totpSecret) }

    /**
     * Returns an Avanza client without logging in. The client can be used for endpoints that don't
     * require authentication, such as price charts and exchange rates. Calling endpoints that require
     * authentication will fail.
     *
     * @return an unauthenticated Avanza client
     */
    fun unauthenticatedClient(debugPrintouts: Boolean = false): AvanzaClient = AvanzaClient(debugPrintouts)
}