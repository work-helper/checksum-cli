package cn.mrdear.graal;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author quding
 * @since 2022/5/14
 */
public abstract class AbstractOutputFormat implements OutputFormat {

    protected static final Map<String, Pair<String, BiFunction<String, byte[], Object>>> DIGEST_MAP = new LinkedHashMap<>();

    static {
        BiFunction<String, byte[], Object> defaultDigest = (a, x) -> {
            try {
                byte[] digest = MessageDigest.getInstance(a).digest(x);
                return new BigInteger(1, digest).toString(16);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        };
        BiFunction<String, byte[], Object> base64Digest = (a, x) -> Base64.getEncoder().encodeToString(x);

        DIGEST_MAP.put("MD5", Pair.of("MD5", defaultDigest));
        DIGEST_MAP.put("SHA-1", Pair.of("SHA-1", defaultDigest));
        DIGEST_MAP.put("SHA-256", Pair.of("SHA-256", defaultDigest));
        DIGEST_MAP.put("BASE64", Pair.of("BASE64", base64Digest));
        DIGEST_MAP.put("SHA-512", Pair.of("SHA-512", defaultDigest));
        DIGEST_MAP.put("MD2", Pair.of("MD2", defaultDigest));
        DIGEST_MAP.put("SHA1", Pair.of("SHA-1", defaultDigest));
        DIGEST_MAP.put("SHA256", Pair.of("SHA-256", defaultDigest));
        DIGEST_MAP.put("SHA512", Pair.of("SHA-512", defaultDigest));
        // 针对alfred的特殊形式
        DIGEST_MAP.put("ALFRED", Pair.of("BASE64", base64Digest));
    }

    @Override

    public Integer outputError(String message) {
        System.out.printf("%s%n", message);
        return 1;
    }

}
