## 简介
学习GraalVM以及Picocli的时候，将官方案例改造了下，做成一个顺手的工具，配合alfred使用体验还是很友好的

## 功能

```shell
./checksum -h
                       
Usage: checksum [-hV] [-a=<algorithm>] [-f=<file>] [-o=<output>] [-s=<str>]
Prints the checksum (SHA-256 by default) of a file to STDOUT.
  -a, --algorithm=<algorithm>
                          MD5, SHA-1, SHA-256, ...
  -f, --file=<file>       The file whose checksum to calculate.
  -h, --help              Show this help message and exit.
  -o, --output=<output>   ORIGIN, ALFRED is option, The checksum output format
  -s, --string=<str>      The string whose checksum to calculate.
  -V, --version           Print version information and exit.

```

## Example

**字符串计算摘要**

```shell
$ ./checksum -a md5 -s hello          
input string: hello
5d41402abc4b2a76b9719d911017c592
```

**文件计算摘要**

```shell
$ ./checksum -a md5 -f hello.txt 
input filename: hello.txt
5d41402abc4b2a76b9719d911017c592

```

**Alfred**
这种方式需要在[Release](https://github.com/work-helper/checksum-cli/releases)中下载对应的workflow,安装后即可使用

![image](https://github.com/work-helper/checksum-cli/raw/master/doc/checksum.gif)