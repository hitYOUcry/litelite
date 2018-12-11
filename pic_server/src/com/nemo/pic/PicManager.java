package com.nemo.pic;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PicManager {

    private static volatile PicManager sInstance = null;

    private PicManager() {
    }

    public static PicManager getInstance() {
        if (sInstance == null) {
            synchronized (PicManager.class) {
                if (sInstance == null) {
                    sInstance = new PicManager();
                }
            }
        }
        return sInstance;
    }

    static final SimpleDateFormat sF = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
    static final AtomicInteger sIndexGenerator = new AtomicInteger(0);

    public ArrayList<String> dealPicUploadTask(HttpServletRequest request) {
        DiskFileItemFactory factory = new DiskFileItemFactory();

        String path = request.getRealPath("/upload/");
        File picUploadDir = new File(path);
        if (!picUploadDir.exists()) {
            picUploadDir.mkdir();
        }

        factory.setRepository(picUploadDir);
        factory.setSizeThreshold(1024 * 1024);

        ServletFileUpload upload = new ServletFileUpload(factory);


        ArrayList<String> result = new ArrayList<>();
        String picUrlPath = request.getContextPath().concat("/upload/");
        try {
            List<FileItem> itemList = upload.parseRequest(request);
            for (FileItem item : itemList) {
                String name = item.getFieldName();
                if (item.isFormField()) {
                    String value = item.getString();
                    request.setAttribute(name, value);
                } else {
                    String fileName = sF.format(new Date(System.currentTimeMillis()))
                            + "_" + sIndexGenerator.getAndIncrement() + ".png";
                    String finalPath = path + fileName;
                    saveFileItem(finalPath, item);

                    result.add(Common.SERVER_URL + picUrlPath + fileName);
                }
            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void saveFileItem(String fileFinalPath, FileItem picItem) {
        File file = new File(fileFinalPath);
        try {
            OutputStream out = new FileOutputStream(file);
            InputStream in = picItem.getInputStream();
            out.write(ByteUtils.toByteArray(in));
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
