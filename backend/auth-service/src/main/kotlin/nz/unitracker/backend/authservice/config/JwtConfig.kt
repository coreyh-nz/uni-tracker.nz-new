package nz.unitracker.backend.authservice.config

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.JWSVerificationKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.jwt.JwtClaimValidator
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import kotlin.io.encoding.Base64
import kotlin.time.toKotlinDuration
import java.time.Duration as JavaDuration

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class JwtConfig(
    private val jwtProperties: JwtProperties,
) {
    @ConditionalOnMissingBean
    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val keyFactory = KeyFactory.getInstance("RSA")
        val rsaPrivateKey =
            Base64.decode(jwtProperties.privateKey).let {
                keyFactory.generatePrivate(PKCS8EncodedKeySpec(it)) as RSAPrivateKey
            }
        val rsaPublicKey =
            Base64.decode(jwtProperties.publicKey).let {
                keyFactory.generatePublic(X509EncodedKeySpec(it)) as RSAPublicKey
            }
        val rsaKey =
            RSAKey
                .Builder(rsaPublicKey)
                .privateKey(rsaPrivateKey)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .build()
        return ImmutableJWKSet(JWKSet(rsaKey))
    }

    @Bean
    fun jwtEncoder(jwkSource: JWKSource<SecurityContext>): JwtEncoder = NimbusJwtEncoder(jwkSource)

    @Bean
    fun jwkDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder {
        val jwtProcessor = DefaultJWTProcessor<SecurityContext>()
        val keySelector = JWSVerificationKeySelector(JWSAlgorithm.RS256, jwkSource)
        jwtProcessor.jwsKeySelector = keySelector

        return NimbusJwtDecoder(jwtProcessor).apply {
            val withIssuer = JwtValidators.createDefaultWithIssuer(jwtProperties.issuer)
            val withAudience =
                JwtClaimValidator<List<String>>("aud") {
                    it?.contains(jwtProperties.audience) == true
                }
            setJwtValidator(
                DelegatingOAuth2TokenValidator(withIssuer, withAudience),
            )
        }
    }
}

@ConfigurationProperties(prefix = "jwt")
class JwtProperties
    @ConstructorBinding
    constructor(
        accessLifetime: JavaDuration,
        refreshLifetime: JavaDuration,
        val privateKey: String,
        val publicKey: String,
        val issuer: String,
        val audience: String,
    ) {
        val accessLifetime = accessLifetime.toKotlinDuration()
        val refreshLifetime = refreshLifetime.toKotlinDuration()
    }
