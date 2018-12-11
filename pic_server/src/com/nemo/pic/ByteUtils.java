package com.nemo.pic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteUtils {

    public static byte[] toByteArray(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int len = 0;
        byte[] temp = new byte[1024];

        try {
            while ((len = in.read(temp)) != -1) {
                out.write(temp, 0, len);
            }
        } catch (IOException e) {
        }
        return out.toByteArray();
    }
}
