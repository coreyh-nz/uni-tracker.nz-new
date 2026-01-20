package nz.unitracker.backend.authservice.helper

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import nz.unitracker.backend.commontest.BaseIntegrationTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Import(TestJwtConfig::class)
@BaseIntegrationTest
annotation class AuthIntegrationTest

@TestConfiguration
private class TestJwtConfig {
    private val keyPair: KeyPair by lazy {
        val generator = KeyPairGenerator.getInstance("RSA")
        generator.initialize(2048)
        generator.generateKeyPair()
    }

    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val rsaKey =
            RSAKey
                .Builder(keyPair.public as RSAPublicKey)
                .privateKey(keyPair.private as RSAPrivateKey)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID("test-key")
                .build()

        return ImmutableJWKSet(JWKSet(rsaKey))
    }
}
