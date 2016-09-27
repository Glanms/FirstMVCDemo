package com.gisk.utils;

import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guojianqiang@joymaker.cc on 16/9/26.
 */
public class MatrixToImageWriter {
    private static final int BLACK = 0xFF000000;//用于设置图案的颜色
    private static final int WHITE = 0xFFFFFFFF; //用于背景色

    private MatrixToImageWriter() {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y,  (matrix.get(x, y) ? BLACK : WHITE));
//              image.setRGB(x, y,  (matrix.get(x, y) ? Color.YELLOW.getRGB() : Color.CYAN.getRGB()));
            }
        }
        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        //文本
        int no=1933;
        String content = String.format("%06d",no);
        //设置logo图标
        String logoPath= "/Users/tz_android_gjq/Documents/C_ENTER/IMAGES/bg_test2.jpg";  //中心logo图片路径
        String bgPath="/Users/tz_android_gjq/Documents/C_ENTER/IMAGES/bg_test3.jpg";    //背景图路径
        BufferedImage logo = ImageIO.read(new File(logoPath));
        BufferedImage bg = ImageIO.read(new File(bgPath));
        QRLogoConfig QRLogoConfig = new QRLogoConfig(logo,bg,content);
        Map<String, Object> logoConfig = new HashMap<>();
        image = QRLogoConfig.LogoMatrix(image,logoConfig);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }else{
            System.out.println("图片生成成功！");
        }
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        //文本
        int no=1933;
        String content = String.format("%06d",no);
        //设置logo图标
        String logoPath= "/Users/tz_android_gjq/Documents/C_ENTER/IMAGES/bg_test2.jpg";  //中心logo图片路径
        String bgPath="/Users/tz_android_gjq/Documents/C_ENTER/IMAGES/bg_test3.jpg";    //背景图路径
        BufferedImage logo = ImageIO.read(new File(logoPath));
        BufferedImage bg = ImageIO.read(new File(bgPath));
        QRLogoConfig QRLogoConfig = new QRLogoConfig(logo,bg,content);
        Map<String, Object> logoConfig = new HashMap<>();
        image = QRLogoConfig.LogoMatrix(image,logoConfig);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

}
