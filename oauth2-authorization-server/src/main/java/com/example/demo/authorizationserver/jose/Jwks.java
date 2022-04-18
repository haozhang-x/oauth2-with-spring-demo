package com.example.demo.authorizationserver.jose;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * @author Joe Grandja
 * @since 0.1.0
 */
public final class Jwks {

    private Jwks() {
    }

    public static RSAKey generateRsa() {
        KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // @formatter:off
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        // @formatter:on
    }

    public static ECKey generateEc() {
        KeyPair keyPair = KeyGeneratorUtils.generateEcKey();
        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
        ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
        Curve curve = Curve.forECParameterSpec(publicKey.getParams());
        // @formatter:off
        return new ECKey.Builder(curve, publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        // @formatter:on
    }


    public static ECKey getECKey() {

        String algorithm="EC";

        ClassPathResource privateKeyResource=new ClassPathResource("ecc/ecc-private-key-pkcs8.pem");
        ClassPathResource publicKeyResource=new ClassPathResource("ecc/ecc-public-key.pem");
        try (InputStream privateKeyResourceInputStream = privateKeyResource.getInputStream();
             InputStream publicKeyResourceInputStream = publicKeyResource.getInputStream()
        ){
            ECPrivateKey privateKey = (ECPrivateKey)PemUtils.readPrivateKey(privateKeyResourceInputStream, algorithm);
            ECPublicKey publicKey = (ECPublicKey)PemUtils.readPublicKey(publicKeyResourceInputStream, algorithm);
            Curve curve = Curve.forECParameterSpec(publicKey.getParams());
            return new ECKey.Builder(curve, publicKey)
                    .privateKey(privateKey)
                    .algorithm(JWSAlgorithm.ES256)
                    .keyID(UUID.randomUUID().toString())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static RSAKey getRSAKey() {

        String algorithm="RSA";

        ClassPathResource privateKeyResource=new ClassPathResource("rsa/rsa_pkcs8_private.pem");
        ClassPathResource publicKeyResource=new ClassPathResource("rsa/rsa_public.pem");
        try (InputStream privateKeyResourceInputStream = privateKeyResource.getInputStream();
             InputStream publicKeyResourceInputStream = publicKeyResource.getInputStream()
        ){
            RSAPrivateKey privateKey = (RSAPrivateKey) PemUtils.readPrivateKey(privateKeyResourceInputStream, algorithm);
            RSAPublicKey publicKey = (RSAPublicKey)PemUtils.readPublicKey(publicKeyResourceInputStream, algorithm);
            return new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(UUID.randomUUID().toString())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static OctetSequenceKey generateSecret() {
        SecretKey secretKey = KeyGeneratorUtils.generateSecretKey();
        // @formatter:off
        return new OctetSequenceKey.Builder(secretKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        // @formatter:on
    }
}