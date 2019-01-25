package com.learning.file;

import com.google.common.io.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @Author xuetao
 * @Description: 新建文件测试FileNotFoundException
 * @Date 2019-01-25
 * @Version 1.0
 */
public class FileDemo {

    /**
     * 当hello文件夹不存在时报错如下...
     * java.io.FileNotFoundException: /Users/.../Documents/hello/hello.xls (No such file or directory)
     * @param args
     */

    public static void main(String[] args) {
        File tmpDir = new File("/Users/xuetao/Documents/");
        File file = new File(tmpDir, "/hello/hello.xls");
        if (file.exists()) {
            System.out.println("file is exists: " + file.getAbsoluteFile());
        } else{
            System.out.println("file is not exists: "+ file.getAbsoluteFile());
            try {
                OutputStream outputStream = new FileOutputStream(file);

                System.out.println(outputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
