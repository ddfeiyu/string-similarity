package com.example.movie.recommand.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author dd
 * @Date 2022/7/8-16:40
 * @function
 */
public class FileUploadUtil{
        public static void uploadFile(byte[] file, String filePath, String fileName) throws IOException {
            File targetFile = new File(filePath);
            if(!targetFile.exists()){
                targetFile.mkdirs();
                System.out.println("存储");
            }
            FileOutputStream out = new FileOutputStream(filePath+fileName);
            out.write(file);
            out.flush();
            out.close();
        }
}
