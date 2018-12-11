package com.nemo.pic;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PicUploadServlet extends javax.servlet.http.HttpServlet {

    private static final Logger logger = Logger.getLogger("PicUploadServlet");

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        logger.log(Level.INFO, "doGet...");
        doPost(request, response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        logger.log(Level.INFO, "doPost...");
        List<String> savePicUrls = PicManager.getInstance().dealPicUploadTask(request);

        String responseStr = buildResponseString(savePicUrls);
        response.getWriter().println(responseStr);

    }

    private String buildResponseString(List<String> savePicUrls) {
        StringBuilder sb = new StringBuilder();
//        sb.append("{");
        for (String item : savePicUrls) {
            sb.append(item);
            sb.append(",");
        }
//        sb.append("}");
        return sb.toString();
    }
}
