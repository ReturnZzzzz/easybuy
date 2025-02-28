package com.a.easybuy.util;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Properties;
import java.util.Random;
@Component
public class RandomCodeUtil {

    private static final String CHAR_SET = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm123456789"; // 避免易混淆字符
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;

    // 生成随机验证码字符串
    public static String generateCode(int length) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(CHAR_SET.charAt(random.nextInt(CHAR_SET.length())));
        }
        return code.toString();
    }

    // 生成验证码图片
    public static BufferedImage generateImage(String code) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 绘制干扰线
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g.drawLine(x1, y1, x2, y2);
        }

        // 绘制验证码
        g.setFont(new Font("Arial", Font.BOLD, 24));
        for (int i = 0; i < code.length(); i++) {
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g.drawString(String.valueOf(code.charAt(i)), 20 + i * 24, 28);
        }

        g.dispose();
        return image;
    }
}
