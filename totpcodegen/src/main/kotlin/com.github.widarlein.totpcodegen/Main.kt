package com.github.widarlein.totpcodegen

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator
import com.google.common.io.BaseEncoding
import java.time.Instant
import javax.crypto.spec.SecretKeySpec
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Need the totp secret as argument")
        exitProcess(0)
    }
    val totpSecret = args[0]

    val baseEncoding = BaseEncoding.base32()
    val totp = TimeBasedOneTimePasswordGenerator()
    val secretKeySpec = SecretKeySpec(baseEncoding.decode(totpSecret), "SHA1")

    val now = Instant.now()
    val totpCode: Int = totp.generateOneTimePassword(secretKeySpec, now)

    println("TOTP CODE: ${totpCode.toTotpString()}")
}

private fun Int.toTotpString(): String {
    val code = this.toString()
    check(code.length <= 6) {"Totp codes must be at most 6 character, this Int does not qualify"}
    return code.padStart(6, '0')
}