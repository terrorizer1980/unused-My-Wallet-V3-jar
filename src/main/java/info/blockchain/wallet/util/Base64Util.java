package info.blockchain.wallet.util;

import com.lambdaworks.codec.Base64;

import org.apache.commons.codec.binary.StringUtils;

import javax.annotation.Nonnull;

public final class Base64Util {

    private Base64Util() {
        throw new AssertionError("Cannot be instantiated");
    }

    /**
     * Returns a UTF-8 String from a byte[] input in Base64 format. This helper method replaces
     * {@link org.apache.commons.codec.binary.Base64#encodeBase64String(byte[])}, as Android devices
     * have commons.codec V1.3 in the classpath and the method was added in 1.4. Because the lib is
     * in the boot classpath, the packaged library here (1.10) isn't loaded, and as such invoking
     * the method throws a {@link NoSuchMethodError} exception.
     *
     * @param input A byte array in Base64
     * @return A String in UTF-8
     */
    public static String encodeBase64String(byte[] input) {
        return StringUtils.newStringUtf8(getStringToBytes(encode(input)));
    }

    public static String decodeBase64(@Nonnull String input) {
        return StringUtils.newStringUtf8(decode(input));
    }

    private static byte[] decode(@Nonnull String input) {
        return Base64.decode(input.toCharArray());
    }

    private static char[] encode(byte[] bytes) {
        return Base64.encode(bytes);
    }

    private static byte[] getStringToBytes(char[] chars) {
        return new String(chars).getBytes();
    }
}
