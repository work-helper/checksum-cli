package cn.mrdear.graal;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author quding
 * @since 2022/5/14
 */
public class AlfredOutputFormat extends AbstractOutputFormat {

    @Override
    public Integer output(byte[] fileContents, String algorithm, Object origin) throws Exception {
        String originText = null;

        if (origin instanceof String) {
            originText = "input string: " + origin;
        } else if (origin instanceof File) {
            originText = "input filename: " + ((File)origin).getName();
        }

        byte[] digest = MessageDigest.getInstance(algorithm).digest(fileContents);
        System.out.println(originText);
        System.out.printf("%0" + (digest.length*2) + "x%n", new BigInteger(1, digest));
        return 0;
    }

}
