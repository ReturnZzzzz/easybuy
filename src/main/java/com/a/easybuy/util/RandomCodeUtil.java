package com.a.easybuy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
@Component
public class RandomCodeUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获得四位数的随机验证码
     * @return
     */
    public String getString(int number){
        logger.info("GetImgUtil getString parms:number"+number);
        String str = "123456789qwertyuiopasdfghjklzxvcbnmZXCVBNMASDFGHJKLQWERTYUIOP";//
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i=0;i<number;i++){
            int index = random.nextInt(str.length());
            sb.append(str.charAt(index));
        }
        logger.debug("GetImgUtil getString parms:number"+number+"returnValue"+sb.toString());
        return sb.toString();
    }


    /**
     * 前台显示图片
     * @param response
     */
    public void getImg(HttpServletResponse response, String str) throws IOException {
        logger.info("UtilController getImg str:"+str);
        //创建图片
        int width = 120;//设置图片的长和宽
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        //设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        //绘制干扰线
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            g.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
        }

        //绘制验证码文字
        g.setFont(new Font("Arial", Font.BOLD, 24));
        for (int i = 0; i < str.length(); i++) {
            g.setColor(new Color(random.nextInt(128), random.nextInt(128), random.nextInt(128)));
            g.drawString(String.valueOf(str.charAt(i)), 25*i+10,28);
        }
        response.setContentType("image/jpeg");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image,"jpeg",os);
        os.close();
        logger.debug("UtilController getImg parms:"+str+",returnValue:null");
    }
}
