package com.notjuststudio.engine3dgame.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by George on 21.01.2017.
 */
public class StringUtil {

    public static String parseFile(String filePath) {
        StringBuilder shaderSource = new StringBuilder();
        try{
            InputStream in = Class.class.getResourceAsStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("\n");
            }
            reader.close();
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return shaderSource.toString();
    }

    public static String packageToString(Class object) {
        return "/" + (object.getPackage().toString().split("\\s+")[1].replace(".", "/")) + "/";
    }

    public static String replace(String str, int index, int length, String source) {
        return str.substring(0, index) + source + str.substring(index + length, str.length());
    }

}
