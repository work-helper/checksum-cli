package cn.mrdear.graal;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.BufferedReader;
import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.MessageDigestSpi;
import java.security.Security;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author quding
 * @since ${DATE}
 */
@Command(name = "checksum", mixinStandardHelpOptions = true, version = "checksum 1.0",
    description = "Prints the checksum (SHA-256 by default) of a file to STDOUT.")
public class CheckSum implements Callable<Integer> {

    @Option(names = {"-f","--file"}, description = "The file whose checksum to calculate.")
    private File file;

    @Option(names = {"-s","--string"}, description = "The string whose checksum to calculate.")
    private String str;

    @Option(names = {"-o","--output"}, description = "ORIGIN, ALFRED is option, The checksum output format")
    private String output = "origin";

    @Option(names = {"-a", "--algorithm"}, description = "MD5, SHA-1, SHA-256, ...")
    private String algorithm = "SHA-256";

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        byte[] fileContents = null;
        Object origin = null;

        OutputFormat format = "alfred".equalsIgnoreCase(output) ? AlfredOutputFormat.INSTANCE : OriginOutputFormat.INSTANCE;

        if (!AbstractOutputFormat.DIGEST_MAP.containsKey(algorithm.toUpperCase())) {
            return format.outputError("algorithm not support");
        }

        // 先读取字符串
        if (null != str && str.length() > 0) {
            fileContents = str.getBytes(StandardCharsets.UTF_8);
            origin = str;
        }
        // 在尝试文件
        if (null != file) {
            fileContents = Files.readAllBytes(file.toPath());
            origin = file;
        }

        if (null == fileContents) {
            return format.outputError("no input can found");
        }

        format.output(fileContents, algorithm.toUpperCase(), origin);
        return 0;
    }

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String... args) {
        int exitCode = new CommandLine(new CheckSum()).execute(args);
        System.exit(exitCode);
    }
}