package auto.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.lang.Validate;

/**
 * Thread-safe.
 * 
 */
public class Md5Utils {
    
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * Thread local instance.
     */
    private static final ThreadLocal<MessageDigest> LOCAL_MD5 = new ThreadLocal<MessageDigest>() {
        @Override
        protected MessageDigest initialValue() {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    };
    
    /**
     * @param bytes
     *            non-null
     * @return 128 bits MD5 result
     */
    public static String sign(byte[] bytes) {
        Validate.notNull(bytes);
        MessageDigest md = LOCAL_MD5.get();
        return getFormattedText(md.digest(bytes));
    }

    /**
     * @param bytes
     *            non-null
     * @param offset
     *            see {@link MessageDigest#update(byte[], int, int)}
     * @param len
     *            see {@link MessageDigest#update(byte[], int, int)}
     * @return 128 bits MD5 result
     */
    public static byte[] sign128bit(byte[] bytes, int offset, int len) {
        Validate.notNull(bytes);
        MessageDigest md = LOCAL_MD5.get();
        md.update(bytes, offset, len);
        return md.digest();
    }

    /**
     * @param bytes
     *            non-null
     * @return 128 bits MD5 result
     */
    public static byte[] sign128bit(byte[] bytes) {
        Validate.notNull(bytes);
        MessageDigest md = LOCAL_MD5.get();
        return md.digest(bytes);
    }

    /**
     * @param bytes
     *            non-null
     * @return 64 bits MD5 result
     */
    public static long sign64bit(byte[] bytes) {
        Validate.notNull(bytes);
        MessageDigest md = LOCAL_MD5.get();
        byte[] result = md.digest(bytes);
        long code = (((long) result[7]) << 56) + (((long) result[6]) << 48)
                + (((long) result[5]) << 40) + (((long) result[4]) << 32)
                + (((long) result[3]) << 24) + (((long) result[2]) << 16)
                + (((long) result[1]) << 8) + (((long) result[0]));
        return code;
    }
    
    public static byte[] long2Bytes(long v) {
        byte[] result = new byte[8];
        for (int i = 0; i < 8; i++) {
            result[i] = (byte)(v & 0xff);
            v >>= 8;
        }
        return result;
    }

    /**
     * @param bytes
     *            non-null
     * @return 32 bits MD5 result
     */
    public static int sign32bit(byte[] bytes) {
        Validate.notNull(bytes);
        MessageDigest md = LOCAL_MD5.get();
        byte[] result = md.digest(bytes);
        int code = (((int) result[3]) << 24) + (((int) result[2]) << 16)
                + (((int) result[1]) << 8) + (((int) result[0]));
        return code;
    }
    
    private static final long NUMBER_SET_SIZE = 10;
    
    private static final long ALPH_SET_SIZE = 26;
    
    private static final long CHAR_SET_SIZE = ALPH_SET_SIZE * 2 + NUMBER_SET_SIZE;
    
    private static final long BASE_CODE = CHAR_SET_SIZE * CHAR_SET_SIZE * CHAR_SET_SIZE * CHAR_SET_SIZE * CHAR_SET_SIZE * CHAR_SET_SIZE + 1;
    
    public static String number2String(long code) {
        code = code - Integer.MIN_VALUE + BASE_CODE;
        StringBuilder sb = new StringBuilder();
        while (code > 0) {
            long num = code % CHAR_SET_SIZE;
            if (num < NUMBER_SET_SIZE) {
                sb.append((char)('0' + num));
            } else if (num < NUMBER_SET_SIZE + ALPH_SET_SIZE) {
                sb.append((char)('A' + num - NUMBER_SET_SIZE));
            } else {
                sb.append((char)('a' + num - NUMBER_SET_SIZE - ALPH_SET_SIZE));
            }
            code = code / CHAR_SET_SIZE;
        }
        return sb.toString();
    }
    
    public static long string2Number(String str) {
        long code = 0;
        long base = 1;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            long num = 0;
            if (ch >= '0' && ch <= '9') {
                num = ch - '0';
            } else if (ch >= 'A' && ch <= 'Z') {
                num = NUMBER_SET_SIZE + ch - 'A';
            } else if (ch >= 'a' && ch <= 'z') {
                num = ALPH_SET_SIZE + NUMBER_SET_SIZE + ch - 'a';
            }
            code += base * num;
            base *= CHAR_SET_SIZE;
        }
        code = code - BASE_CODE + Integer.MIN_VALUE;
        return code;
    }
    
    public static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
    
    private static final Random rand = new Random();
    
    /**
     * generate random string in 6 length.
     */
    public static String getRandomString() {
        return number2String(rand.nextInt());
    }
    
    public static void main(String[] args) {
        System.out.println(getRandomString());
//        System.out.println(number2String(rand.nextInt()));
//        System.out.println(string2Number("-bfd6f0cd84c43f"));
//        Long num = Long.parseLong("A4D38FF23EAD593E98734F2AA042106B", 16);
//        System.out.println(num);
//        String s = "A4D38FF23EAD593E98734F2AA042106B";
//        for (int i = 0; i < s.length(); i+=2) {
//            char x1 = s.charAt(i);
//            char x2 = s.charAt(i + 1);
//            long a = (long)x1 * 16 + (long)x2;
//            System.out.println(a);
//        }
    }
    
}
