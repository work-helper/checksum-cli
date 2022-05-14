package cn.mrdear.graal;

import java.security.NoSuchAlgorithmException;

/**
 * @author quding
 * @since 2022/5/14
 */
public interface OutputFormat {

    /**
     * 输出错误信息,并且结束当前应用
     * @param message 写入到STDOUT的错误的信息
     * @return 返回码
     */
    Integer outputError(String message);

    /**
     * 输出方法
     * @param fileContents 文件内容
     * @param algorithm 签名算法
     * @param origin 原始输入
     * @return 返回码
     */
    Integer output(byte[] fileContents, String algorithm, Object origin) throws Exception;

}
