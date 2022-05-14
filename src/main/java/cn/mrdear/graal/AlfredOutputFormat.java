package cn.mrdear.graal;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * @author quding
 * @since 2022/5/14
 */
public class AlfredOutputFormat extends AbstractOutputFormat {

    public static final AlfredOutputFormat INSTANCE = new AlfredOutputFormat();

    /**
     * {"items": [
     *     {
     *         "uid": "desktop",
     *         "title": "Desktop",
     *         "subtitle": "~/Desktop",
     *         "arg": "~/Desktop",
     *         "icon": {
     *             "type": "fileicon",
     *             "path": "~/Desktop"
     *         }
     *     }
     * ]}
     */
    @Override
    public Integer output(byte[] fileContents, String algorithm, Object origin) throws Exception {
        String originText = null;

        if (origin instanceof String) {
            originText = (String)origin;
        } else if (origin instanceof File) {
            originText = ((File)origin).getName();
        }

        // alfred 模式下,尽可能输出多的签名信息
        StringBuilder builder = new StringBuilder("{\"items\": [");

        Set<String> contains = new HashSet<>(5);

        builder.append('{');
        builder.append("\"title\": \"").append("ORIGIN").append("\",");
        builder.append("\"icon\": {\"path\": \"ORIGIN.png\"},");
        builder.append("\"subtitle\": \"").append(originText).append("\",");
        builder.append("\"arg\": \"").append(originText).append("\"");
        builder.append("},");

        for (Pair<String, BiFunction<String, byte[], Object>> pair : DIGEST_MAP.values()) {
            if (contains.contains(pair.left)) {
                continue;
            }
            contains.add(pair.left);

            builder.append('{');
            builder.append("\"title\": \"").append(pair.left).append("\",");
            builder.append("\"icon\": {\"path\": \"").append(pair.left).append(".png\"},");
            Object digest = pair.right.apply(pair.left, fileContents);

            builder.append("\"subtitle\": \"").append(digest).append("\",");
            builder.append("\"arg\": \"").append(digest).append("\"");
            builder.append("},");
        }
        // 去除尾部逗号
        builder.deleteCharAt(builder.length() - 1);
        builder.append("]}");
        System.out.printf("%s%n", builder);
        return 0;
    }

}
