package com.mercadolibre.apps.sim.util;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.security.MessageDigest;
import java.security.DigestInputStream;


public final class FileHelper extends Object {

    // inputStream - FileInputStream where the contents have been saved
    public static String computeMD5(InputStream stream) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");

            DigestInputStream digestStream = new DigestInputStream(stream, digest);
            while (digestStream.read() != -1) {
                ;
            }
        } 
		catch(Exception ex) {
            throw new RuntimeException(ex);
        }

        byte[] digestBytes = digest.digest();

        StringBuffer hexString = new StringBuffer();
        for (byte digestByte: digestBytes) {
            hexString.append(Integer.toHexString(0xFF & digestByte));
        }
        return hexString.toString();
    }
}
