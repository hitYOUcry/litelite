package com.tencent.nemo;

import com.sun.javafx.util.Logging;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Watermark;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

public class PicMain extends HttpServlet {

    private static final String TAG = "PicMain";

    @Override
    public void init() throws ServletException {
        super.init();
        Logging.getJavaFXLogger().info(TAG + "[init()]");
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Logging.getJavaFXLogger().info(TAG + "[init(ServletConfig config)]");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logging.getJavaFXLogger().info(TAG + "[service]");
        ServletContext context = getServletContext();

        BufferedImage oriImg = ImageIO.read(req.getInputStream());


        File dir = new File(context.getRealPath("WEB-INF").concat("/pics/ori/"));
        if(!dir.exists()){
            dir.mkdir();
        }
        File originalFile = new File(context.getRealPath("WEB-INF").concat("/pics/ori/" + System.currentTimeMillis() + ".png"));
        if(!originalFile.exists()){
            originalFile.createNewFile();
        }
        FileOutputStream fOut = new FileOutputStream(originalFile);
        ImageIO.write(oriImg,"png",fOut);


        String waterMarkPicPath = context.getRealPath("WEB-INF").concat("/pics/google.png");
        BufferedImage bImg = ImageIO.read(new FileInputStream(waterMarkPicPath));
        Watermark wm = new Watermark(Positions.BOTTOM_CENTER, bImg, 0.5f);

        BufferedImage oriPicsWithWatermark = Thumbnails.of(oriImg)
                .watermark(wm)
                .size(oriImg.getWidth(), oriImg.getHeight())
                .asBufferedImage();

        OutputStream out = resp.getOutputStream();
        byte[] temp = toByteArray(getImageStream(oriPicsWithWatermark));
        out.write(temp);

        fOut.close();
        out.flush();
        out.close();

    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int readSize;
        byte[] data = new byte[2 * 1024];
        while ((readSize = in.read(data)) != -1) {
            buffer.write(data, 0, readSize);
        }
        buffer.flush();

        return buffer.toByteArray();
    }

    public InputStream getImageStream(BufferedImage bimage) {
        InputStream is = null;
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut;
        try {
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bimage, "png", imOut);
            is = new ByteArrayInputStream(bs.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        Logging.getJavaFXLogger().info(TAG + "[doGet]");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        Logging.getJavaFXLogger().info(TAG + "[doPost]");
    }


}
