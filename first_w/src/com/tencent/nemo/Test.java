package com.tencent.nemo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Test {
    static final String URL = "http://localhost:8080/first_w_war_exploded/aa";

    public static void uploadPic(){
        try {
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.addRequestProperty("User-Agent", "UnionSDK");
            conn.addRequestProperty("Content-Type", "application/octet-stream");

            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
