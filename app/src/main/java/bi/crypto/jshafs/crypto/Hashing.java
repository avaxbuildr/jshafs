/*
 * jshafs - Hash every file on a filesystem and save the hashes to a SQLite DB. Based on shafs
 *
 * @author avaxbuildr
 *
 * Documentation at https://crypto.bi
 *
 */

package bi.crypto.jshafs.crypto;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {

    static MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String sha256String(String in) {
        return sha256Bytes(in.getBytes(StandardCharsets.UTF_8));
    }

    public static String sha256Bytes(byte[] in) {
        digest.reset();
        digest.update(in);
        byte[] d = digest.digest();
        return new BigInteger(1, d).toString(16);
    }
    public static String sha256Path(Path p) {
        try {
            byte[] contents = Files.readAllBytes(p);
            return sha256Bytes(contents);
        } catch (IOException e) {
            return null;
        }

    }
}
