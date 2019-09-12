# Kavanza
A Kotlin port of the Node.js lib wrapping the unofficial Avanza API, https://github.com/fhqvst/avanza.
As with the original this is a proof of concept, made to practice Kotlin and not for serious use.

I am not affiliated with Avanza and the API might change in any way at any time, rendering this library unusable. 

## TOTP Secret
In order to log in to Avanza, you need to enable two-factor authentication and establish a TOTP secret.

#### Getting a jar
You will need to confirm the TOTP secret later. You can do that with an authenticator app, but I have also included a jar
for this purpose. Depending how much you trust me you can use it straight from the repo or inspect the code first and build
it yourself.

##### I trust you with my life savings
Great! Just use the `totpcodegen-1.0.jar` from the root of this repo.
##### Fat chance, also I enjoy wearing tin foil hats
Sigh. Code is in totpcodegen
```console
cd totpcodegen
./gradlew build
ls build/libs/totpcodegen-1.0.jar
```

On the Avanza web page, log in and:
1. Go to _Mina Sidor > Profil > Sajtinställningar > Tvåfaktorsinloggning_ and click _Återaktivera_
2. Click _Ja, återaktivera_
3. Click _Aktivera_
4. Under **Annan app för tvåfaktorsinloggning** click _Nu har jag laddat ner appen, gå vidare_
5. Enter your password
6. Click _Kan du inte scanna QR-koden?_ to reveal the TOTP secret, 32 character long base 32 string.
7. Confirm Secret by generating a TOTP code, perhaps with the totpcodegen-1.0.jar
    * `java -jar totpcodegen-1.0.jar <YOUR_TOTP_SECRET_HERE>`
8. Save your secret somewhere secret.

## Installation
Gradle/Maven
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
    implementation 'com.github.widarlein:kavanza:1.0'
}
```

## Usage
By calling `Avanza.connect()` with proper arguments, the lib will log you in and return a client for further interactions.
Check out all capabilities of the AvanzaClient by checking the docs or reading the code.
```kotlin
import com.github.widarlein.kavanza.Avanza
import com.github.widarlein.kavanza.AvanzaClient
import com.github.widarlein.kavanza.model.*

val avanzaClient: AvanzaClient = Avanza.connect(UserCredentials("<USERNAME>", "<PASSWORD>"), "<TOTP_SECRET>")
val overview = avanzaClient.getOverview()

println("User account overview: $overview")
```

## Docs
You find the dokka docs here, baby https://widarlein.github.io/kavanza/

## Known Issues
* `AvanzaClient.getInspirationLists()` does not currently work and will cast an exception if you try to use it. The response
    json contains some polymorphism depending on what type of list is returned and I haven't worked out a good way of handling that yet.
    The only type actually working is _STOCK_, but this endpoint can return several different types.
* `AvanzaClient.getInspirationList()` has the same problem as above. Only type responses of type _STOCK_ would work, but
    the only types available to request are different lists with type _FUND_.
* Real time data is not yet implemented.    
## Disclaimer 
Use at own risk, I'm not responsible for anything you do with this.