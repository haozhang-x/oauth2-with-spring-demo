package com.example.demo.authorizationserver.jose;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
public class PemUtils {
    private static byte[] parsePEM(File pemFile) throws IOException {
        if (!pemFile.isFile() || !pemFile.exists()) {
            throw new FileNotFoundException(String.format("The file '%s' doesn't exist.", pemFile.getAbsolutePath()));
        }
        PemReader reader = new PemReader(new FileReader(pemFile));
        PemObject pemObject = reader.readPemObject();
        byte[] content = pemObject.getContent();
        reader.close();
        return content;
    }


    private static byte[] parsePEM(InputStream inputStream) throws IOException {
        InputStreamReader inReader = new InputStreamReader(inputStream);
        PemReader reader = new PemReader(inReader);
        PemObject pemObject = reader.readPemObject();
        byte[] content = pemObject.getContent();
        reader.close();
        return content;
    }

    private static PublicKey getPublicKey(byte[] keyBytes, String algorithm) {
        PublicKey publicKey = null;
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            publicKey = kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not reconstruct the public key, the given algorithm could not be found.");
        } catch (InvalidKeySpecException e) {
            System.out.println("Could not reconstruct the public key");
        }

        return publicKey;
    }

    private static PrivateKey getPrivateKey(byte[] keyBytes, String algorithm) {
        PrivateKey privateKey = null;
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            privateKey = kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not reconstruct the private key, the given algorithm could not be found.");
        } catch (InvalidKeySpecException e) {
            System.out.println("Could not reconstruct the private key");
        }

        return privateKey;
    }

    public static PublicKey readPublicKey(String filepath, String algorithm) throws IOException {
        byte[] bytes = PemUtils.parsePEM(new File(filepath));
        return getPublicKey(bytes, algorithm);
    }

    public static PublicKey readPublicKey(InputStream inputStream, String algorithm) throws IOException {
        byte[] bytes = PemUtils.parsePEM(inputStream);
        return getPublicKey(bytes, algorithm);
    }

    public static PrivateKey readPrivateKey(String filepath, String algorithm) throws IOException {
        byte[] bytes = PemUtils.parsePEM(new File(filepath));
        return getPrivateKey(bytes, algorithm);
    }

    public static PrivateKey readPrivateKey(InputStream inputStream, String algorithm) throws IOException {
        byte[] bytes = PemUtils.parsePEM(inputStream);
        return getPrivateKey(bytes, algorithm);
    }

}
