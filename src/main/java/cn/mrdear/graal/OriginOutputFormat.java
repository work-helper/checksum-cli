package cn.mrdear.graal;

import java.io.File;
import java.util.function.BiFunction;

/**
 * @author quding
 * @since 2022/5/14
 */
public class OriginOutputFormat extends AbstractOutputFormat {

    public static final OriginOutputFormat INSTANCE = new OriginOutputFormat();

    @Override
    public Integer output(byte[] fileContents, String algorithm, Object origin) throws Exception {
        String originText = null;

        if (origin instanceof String) {
            originText = "input string: " + origin;
        } else if (origin instanceof File) {
            originText = "input filename: " + ((File)origin).getName();
        }

        Pair<String, BiFunction<String, byte[], Object>> pair = DIGEST_MAP.get(algorithm);
        Object digest = pair.right.apply(pair.left, fileContents);

        System.out.println(originText);
        System.out.printf("%s%n", digest);
        return 0;
    }

}
