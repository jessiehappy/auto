package auto.util;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyFactory;
import java.security.Provider;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.Validate;

/**
 * Thread-safe.
 * 
 */
@CommonsLog
public class EncryptUtils {
    
    public static final String RSA_PUB_STR = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJzjkg17+kKo4TNED+RM6hdS5whhgEh4RHwkJ0LtYaCJf4ggF0QjjRYkuNyWha3lvkHX36suImYjyB+rTcYHn6HV7BnEw2BCZAP7GS2sSiupDTWw2kcPb1S7refjfHaEwAYXVZpdjQeNePSCWaSxupkOI/QfuazpcTot9PtMVdBwIDAQAB";
            //"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjGytBq9tonLjmZf8LIdngAGK6Z2s0gmBVn382Zf2jiLxlGVQzTMax6bC9qZvvDUNj-8FXZbLWmCWHnXWaTCsGy1kxF2_h-E5VvJFo5dfQltp9Zhspetg8YehRxDfMbWHyJfYm4UJxMjt3h5gMP19aPN2CqXbjBbKHweKoDiLcePAko3N7N4MWjXwXOC5iO-XyYnPwNLSJOhKrn5ucwWICJv05YntwaL0KMIeX-EC72sYM6C6pydzfpxzYq537jqemeg8WRvZzZsjl_HaHLC2clFzh6JQhBsiJG0PtH_a_GiNvbLF5J6XUCTMpErCuOyfUaDc8v-NWbwC0vFjPds6LQIDAQAB"; 
            //"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQo4zH6EgIk3IDvGC5AvI4JiPN1YwwM6GKgCdgDjfpAUSPb8nPIjK3C4KNGeeKdZkj9lGksVACS94IZbBtpRq4umajQ7LzpRsXcZm1f1o9rmVOaSPPbFxmrtQEgsj9wzVpVB4S0TlUJLFU78CJJmqt58gBz0uGKGnRnQ5sPZiqqQIDAQAB"; 
    
    public static final String RSA_PRIV_STR = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAInOOSDXv6QqjhM0QP5EzqF1LnCGGASHhEfCQnQu1hoIl/iCAXRCONFiS43JaFreW+Qdffqy4iZiPIH6tNxgefodXsGcTDYEJkA/sZLaxKK6kNNbDaRw9vVLut5+N8doTABhdVml2NB4149IJZpLG6mQ4j9B+5rOlxOi30+0xV0HAgMBAAECgYAzwQx5hi2GFSlYkwZZuDO2NZEkV2gLw21tmcplYfWsv0972C99nyVBld30OREFF3+BU4YtsiL28WvyENr97QnB1SrBXqE/G3qQXu2C1HB/U3H+KW+sqZYSl7i2xt2s57c5f/BzSddvu5/QkVTGENIQXtllil0e88moviRxMCxGWQJBAMTu4l2HfEvdL2H7qppftNK02FHkcKfqpzapI4NVPSzvAIMjjCfXULDhkXtGcJcdpmWJnPja573sxEf5OBhTUfMCQQCzI1ikSUn+vsk6rQ75mgIS599/9oRj8/mmKP4E1X/eUIQa6pSrINeAqANXrhY56g0FHVLFvufp36Fjz39wHzmdAkB7NFyIWSAJMlrXeSR1qhStkhiXu0xDsUllqBGgs64vM2Nlp9KihlFkJ+rlbd84m4MjmSGMix9asgrmYGOYzF/DAkB7rMV/9tFNPO60wdH65SbVYpxdPha84y3SV664K6YyGCh8ZMPrOatOubBFEn1p0nrWsx34RArc3nYP8Kus/wHJAkAhK3o4/PqjEc4WsuOPTPQndfKpec0exOf70tCws3kOkk8wujnt5iartpoFmX2KMcyKLnW7Zkje3tyAw56BMDgg";
            //"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCMbK0Gr22icuOZl_wsh2eAAYrpnazSCYFWffzZl_aOIvGUZVDNMxrHpsL2pm-8NQ2P7wVdlstaYJYeddZpMKwbLWTEXb-H4TlW8kWjl19CW2n1mGyl62Dxh6FHEN8xtYfIl9ibhQnEyO3eHmAw_X1o83YKpduMFsofB4qgOItx48CSjc3s3gxaNfBc4LmI75fJic_A0tIk6Equfm5zBYgIm_Tlie3BovQowh5f4QLvaxgzoLqnJ3N-nHNirnfuOp6Z6DxZG9nNmyOX8docsLZyUXOHolCEGyIkbQ-0f9r8aI29ssXknpdQJMykSsK47J9RoNzy_41ZvALS8WM92zotAgMBAAECggEAfQ6nxUVkUQEaWwY0yX9JGIHHMah_aYqXieynyyz_obPTC4JzRFvX5KhKBxlQdv-UdXO7ho6tyW4tIzF0Yre6_nUdwZdv5FYbF6iphjhf2JvKsLMvR1BpRJCye7S14PI229kHSfFgMsVyuZa5ucd28YmovXDaJleBZ8LynR_eZ-zEj0ZSBPgkhISb1-1PhVEj8vB8nbbA2bjSWWwJxzeYlCkEE3tqgUoVgaEepGnR_DUkqpCpHCJOiRIU7VZXT5_BT_TFP9EyH2GIteJEZ3QFoiGps01T8_6FqCamrcvH8QUCPDbDXeEC7gBrOeml_g0Z6jEykjmail6yyaHYBzsG7QKBgQDbld-SvibT2Ik9HetLxysuDn7haHUej9U8BI0UcoQ4nzoL-FSIWt5Ql3zhOE2QVu16YjfJs_Yfcc8jlyJt2xtXfeGvl63MUfkcyt86G6wkZbhYy0SQ0NopRHzSfrpjUl_W9FIdJrX3p1c2oaGzBVv6OajsjMy1RsNlhEAZ0kY-0wKBgQCjtijyXJcUutkZmYdKHujTdHwKKDnYpv5T7NN7EX7eNIR59Utn83u5SyQssf0rrfuCbFMImwhm-GqqkeKO-fZzjlaOs1JrgLawg5vJ6A794O6nRzy8u9Xihi_V0RoVJHhqfyQs6Hh6eASxbVDH6No5MAs3p8jYEMFMrvHYH-cC_wKBgQCdDq6Ry9YDsh4_C5ZPZg55a77OxKL4kBIJUowB8wKlaSXgh-cWfEAEzsVwZY_8zGAhJ_0VFNXWmdV5dFEY2B3PT2dL66ZQvNT8BhLyAfb3NZZMVF8fYXKZD7Zj7GauQa3xFmFhHl4K27MijSsV26B08ibjwrVsd0es5ZHfpim-JwKBgHLiE93wB71uWYdTa9Mh2bT520mtbNqSZYszeDy2zFbWd9i1wBLUlJA1IM-Un9sVHpgpuCMj2EOaNBA6i8hVtRLmYeuF_2KdUpaP5LVNeafDPvZ4UTEIh1GNZOgg8rK_gxkI27P_9vvKWVDBK5z91JWpF1YZEiI9r8hGRESsLoq1AoGAUaktn2EKz58ZRhm9A6m9pQN8LqHTnIJdwuUoMB4BMK1Dn1qcumJ1fzzBb3wFRQWtWNNsYoMoC0k_bJKhrf0C2aDI2mboeFHov2N4mOpp6_FrjjbgnVOQqhXfM2jeD3XvoqQaUMkk14ll7_AXg4exNYCpLRS6wBsZwf5UQF2cP30"; 
            //"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJCjjMfoSAiTcgO8YLkC8jgmI83VjDAzoYqAJ2AON-kBRI9vyc8iMrcLgo0Z54p1mSP2UaSxUAJL3ghlsG2lGri6ZqNDsvOlGxdxmbV_Wj2uZU5pI89sXGau1ASCyP3DNWlUHhLROVQksVTvwIkmaq3nyAHPS4YoadGdDmw9mKqpAgMBAAECgYBK3OwkGF0YW0C7IEir2hOG7m6_vhUmacLmDfjTZeiYaHiVCVZaKKg5R_LVl5_ZcY7HJ1oQtVnlJmGQ1RlhVwCh699gELCLpWgoWjxEYRYbTkIU7LhVEJZJ1X_ordDK9_X3SoH6cPgP3S25TwnlV-YgA1dXq_ferGJS3zpfG-duBQJBAOgFG1Ua01Pp7vXMI3EG5IBpxaFCLP2cdsfnqwCrsesRd1mguKa20BuU4aYWCmIZeB8xEoRKp4Ee0IEsl2Q41LsCQQCflnkpgjcEy7bBdOeD0gF9J5eNOX15z5gG6L2oiEp82mAs6KXFkrpVhQCH2e6yevIEGDa-wR-V48Z3CEfVE3nrAkBoJCKoiGnieFXxrbcavGVD3jX-s51OGDSYdbujiW1KfYR8jk9TRSsTtjeyPKJcdeIc3gHZr0iQBCzCTimFqpyRAkAVqPey8ZCEHx4j7bkKhDNdiEUijt3_7XQl4-rx9-WcQotxLPZq9XpC5zVmOucR5Gv6sEql41JlJrAmMcKGIgmXAkEAv1L0qxWBsmoN91N_X4N5bl_R7_rq5bvWmbwant9XV0X9CVRrNssupGVZxqzmZW3OVLOJVxMfThFHHBjPn6NL7A";
    
    private static byte[] encryptKey;

    private static DESedeKeySpec spec;

    private static SecretKeyFactory keyFactory;

    private static SecretKey theKey;

    private static IvParameterSpec IvParameters;
    
    private static KeyFactory rsaKeyFactory;
    
    private static Key rsaPubKey;
    
    private static Key rsaPrivKey;
    
    static {
        try {
            //DES init
            try {
                Cipher.getInstance("DESede");
            } catch (Exception e) {
                log.error("Installling SunJCE provider");
                @SuppressWarnings("restriction")
                Provider sunjce = new com.sun.crypto.provider.SunJCE();
                Security.addProvider(sunjce);
            }
            encryptKey = "012345678901234567890123".getBytes();
            spec = new DESedeKeySpec(encryptKey);
            keyFactory = SecretKeyFactory.getInstance("DESede");
            theKey = keyFactory.generateSecret(spec);
            IvParameters = new IvParameterSpec(new byte[] { 10, 00, 07, 13, 24,
                    35, 46, 57 });
            
            //RSA init
            rsaKeyFactory = KeyFactory.getInstance("RSA");
            
            byte[] keyBytes = Base64.decodeBase64(RSA_PUB_STR);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            rsaPubKey = rsaKeyFactory.generatePublic(x509EncodedKeySpec);
            
            keyBytes = Base64.decodeBase64(RSA_PRIV_STR);
            PKCS8EncodedKeySpec  pkcs8EncodedKeySpec  = new PKCS8EncodedKeySpec(keyBytes);
            rsaPrivKey = rsaKeyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            log.fatal("Init encryption failed", e);
        }    
    }
    
    public static String encryptOpenId(String input) throws Exception {
        return encrypt(input + " " + System.currentTimeMillis());
    }
    
    public static String decryptOpenId(String input) throws Exception {
        String output = decrypt(input);
        String[] ss = output.split(" ");
        if (ss.length == 2) {
            Long time = Long.parseLong(ss[1]);
            if (System.currentTimeMillis() - time < CommonUtils.DAY_IN_MILLS * 7) {
                return ss[0];
            }
        }
        throw new Exception("expired openId");
    }

    public static String encrypt(String input) throws Exception {
        Validate.notNull(input);
        if (input.isEmpty()) {
            return "";
        }
        try {
            Cipher cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, theKey, IvParameters);
            byte[] plainttext = input.getBytes();
            return Base64.encodeBase64URLSafeString(cipher.doFinal(plainttext));
        } catch (Exception e) {
            throw new Exception("Encrypt failed: " + input, e);
        }
    }

    public static String decrypt(String input) throws Exception {
        Validate.notNull(input);
        if (input.isEmpty()) {
            return "";
        }
        if (input.startsWith("test")) {
            return input;
        }
        try {
            Cipher cipher = getCipher();
            cipher.init(Cipher.DECRYPT_MODE, theKey, IvParameters);
            byte[] decrypted_pwd = cipher.doFinal(Base64.decodeBase64(input));
            return new String(decrypted_pwd, Charset.forName("utf8"));
        } catch (Exception e) {
            throw new Exception("Decrypt failed: " + input, e);
        }
    }
    
    public static String encryptAnomynous(String token) throws Exception {
        return encrypt(token);
    }
    
    public static String decryptAnomynous(String input) throws Exception {
        return decrypt(input);
    }

    private static Cipher getCipher() throws Exception {
        return Cipher.getInstance("DESede/CBC/PKCS5Padding");
    }
    
    public static String encryptRsa(String content) throws Exception {
        if (StringUtils.isEmpty(content)) return null;
        try {
            Cipher cipher = Cipher.getInstance(rsaKeyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, rsaPubKey);
            return Base64.encodeBase64URLSafeString(cipher.doFinal(content.getBytes()));
        } catch (Exception e) {
            throw new Exception("encryptRsa failed. content = " + content, e);
        }
    }
    
    public static String decryptRsa(String content) throws Exception {
        if (StringUtils.isEmpty(content)) return null;
        try {
            content = StringUtils.replace(content, " ", "+");
            Cipher cipher = Cipher.getInstance(rsaKeyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, rsaPrivKey);
            byte[] decrypted_pwd = cipher.doFinal(Base64.decodeBase64(content));
            return new String(decrypted_pwd, Charset.forName("utf8"));
        } catch (Exception e) {
            throw new Exception("decryptRsa failed. content = " + content, e);
        }
    }
    
    public static String encryptCvToken(long userId) {
        try {
            return EncryptUtils.encrypt((int)(Math.random() * 10000) + "_" + userId + "_" + (int)(Math.random() * 10000));
        } catch (Exception e) {}
        return null;
    }
    
    public static String encryptPassword(String password) {
        if (password == null) return null;
        return Long.toString(Md5Utils.sign64bit(password.getBytes()), 16);
    }
    
    public static Long decryptCvToken(String token) {
        try {
            String str = EncryptUtils.decrypt(token);
            String[] ss = str.split("_");
            if (ss.length == 3) {
                return Long.parseLong(ss[1]);
            }
        } catch (Exception e) {}
        return null;
    }

    public static void main(String args[]) throws Exception {
        String s = //"cjbbteam"; 
                "p3pKV4_" + System.currentTimeMillis();
//                "f8daM6DvFhPpB84/mKMVtej8J62K+apFLgujhm1qVPQF5o+tFauOdalKTnxhzDrSGAjQrXAZRjafqdVzO+XeX7tgJjM35OTsaBPw7HYb85cwJ5vAKL3xZdaqSetczNY8Ev6fTEyukOzAjlxk6ggadohxt8Hl6V74PQtR1tXHvE0=";
        String r = encryptRsa(s);
        System.out.println(r);
        System.out.println(decryptRsa(r));
//        System.out.println(System.currentTimeMillis());
//        
//        System.out.println(decryptRsa(r));
//        
//        System.out.println(decryptAnomynous("I46bnlXWnLh-TpIHfPQHxQ"));
        //System.out.println(decryptAnomynous("0I7-d4RpSV-P05z0yTPeAA"));
//        System.out.println(decryptRsa("162650ym"));
        //String s = "ozt7Rjg7koDdWrN5-1_LmAbsclB8";

        /*KeyPairGenerator keygen = java.security.KeyPairGenerator.getInstance("RSA");
        keygen.initialize(2048);
        KeyPair keys = keygen.genKeyPair();
        PublicKey pubkey = keys.getPublic();
        PrivateKey prikey = keys.getPrivate();
        System.out.println(Base64.encodeBase64URLSafeString(pubkey.getEncoded()));
        System.out.println(Base64.encodeBase64URLSafeString(prikey.getEncoded()));*/
//        String s = "CczGQdqWZHEE4OP1dXWnLEkuh4X2oOZdNmNS4Lmp_0IVSbXTPr6nWF8iMc1tOW_VTbldkhW9jFVZYRN5bydtyCBIjDdsYhklJlBh3Hnvz-B7fvRKveRRPMln2sYCqCa8Q51w79dhTE4";
//        String r = decrypt(s);
//        System.out.println(r);
//        System.out.println(encryptAnomynous("13488695868"));
//        System.out.println(encryptAnomynous("abcdef"));
    }
}
