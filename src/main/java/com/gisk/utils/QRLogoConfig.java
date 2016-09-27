package com.gisk.utils;

import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class QRLogoConfig {

    private BufferedImage logo;
    private BufferedImage bg;
    private String title;

    public static final String INIT_QR_X = "init_qr_x";  //二维码初始x坐标
    public static final String INIT_QR_Y = "init_qr_y";  //二维码初始y坐标
    public static final String TITLE_FONT = "qr_title_font";  //二维码顶部字体
    public static final String TITLE_POS_X = "qr_title_pos_x";  //文字位置X
    public static final String TITLE_POS_Y = "qr_title_pos_y";  //文字位置Y
    public static final String TITLE_COLOR_BG = "qr_title_color_bg";  //文字背景颜色

    public QRLogoConfig(BufferedImage logo, BufferedImage bg, String title) {
        this.logo = logo;
        this.bg = bg;
        this.title = title;
    }

    /**
     * 设置 背景图 logo
     *
     * @param matrixImage 源二维码图片
     * @return 返回带有logo的二维码图片
     * @throws IOException
     */
    public BufferedImage LogoMatrix(BufferedImage matrixImage, @Nullable Map<String, Object> config) throws IOException, ClassCastException {
        /**
         * 读取Logo图片
         */
//        BufferedImage logo = ImageIO.read(new File("D:\\logo.jpg"));
        // 构建绘图对象
        Graphics2D g2 = bg.createGraphics();

        int matrixWidth = bg.getWidth();
        int matrixHeigh = bg.getHeight();

        System.out.println("Bg. width:" + matrixWidth + " height: " + matrixHeigh);

        //默认
        int qrWidth = matrixImage.getWidth();
        int qrHeigth = matrixImage.getHeight();

        int initQrX = matrixWidth / 3 * 1;
        int initQrY = matrixHeigh / 7 * 4;
        Color titleColor = new Color(244,51,62);

        Font mFont = new Font("TimesRoman", Font.BOLD, 36);
        int titleX = initQrX + qrWidth / 3 + 30;
        int titleY = initQrY - 10;

        if (config != null) {
            if (config.containsKey(INIT_QR_Y))
                initQrX = (int) config.get(INIT_QR_X);
            if (config.containsKey(INIT_QR_Y))
                initQrY = (int) config.get(INIT_QR_Y);
            titleX = initQrX + qrWidth / 3 + 30;
            titleY = initQrY - 10;

            if(config.containsKey(TITLE_FONT))
                mFont = (Font)config.get(TITLE_FONT);
            if(config.containsKey(TITLE_POS_X))
                titleX = (int)config.get(TITLE_POS_X);
            if(config.containsKey(TITLE_POS_Y))
                titleX = (int)config.get(TITLE_POS_Y);
            if(config.containsKey(TITLE_COLOR_BG))
                titleColor = (Color)config.get(TITLE_COLOR_BG);
        }

        //画文字
        g2.setColor(titleColor);
        g2.setFont(mFont); //设置文字大小
        BasicStroke textStroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(textStroke);// 设置笔画对象
        g2.drawString(title, titleX, titleY);

        //画二维码
        g2.drawImage(matrixImage, initQrX, initQrY, qrWidth, qrHeigth, null);

        //开始绘制中心图片
        g2.drawImage(logo, initQrX + qrHeigth / 5 * 2, initQrY + qrHeigth / 5 * 2,
                qrWidth / 5, qrHeigth / 5, null);//绘制
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);// 设置笔画对象
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(initQrX + qrWidth / 5 * 2, initQrY + qrHeigth / 5 * 2, qrWidth / 5, qrHeigth / 5, 20, 20);
        g2.setColor(Color.white);
        g2.draw(round);

        //设置logo 加边框
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(initQrX + qrWidth / 5 * 2 + 2, initQrY + qrHeigth / 5 * 2 + 2,
                qrWidth / 5 - 4, qrHeigth / 5 - 4, 20, 20);
        g2.setColor(new Color(247, 139, 53));
        g2.draw(round2);// 绘制圆弧矩形

        g2.dispose();
        bg.flush();
        return bg;
    }

}