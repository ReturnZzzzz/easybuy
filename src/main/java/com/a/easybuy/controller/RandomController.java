package com.a.easybuy.controller;

import com.a.easybuy.util.RandomCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("code")
public class RandomController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RandomCodeUtil randomCodeUtil;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    /**
     * 在前台显示图片并返回redis中的key值
     */
    @RequestMapping("getImg")
    public void generateCaptchaImageAndStoreKey(HttpServletResponse response) throws IOException {
        logger.info("UtilController generateCaptchaImageAndStoreKey start...");
        String str = randomCodeUtil.getString(4);//生成4位数的随机验证码
        randomCodeUtil.getImg(response,str);//生成图片
        String redisKey ="redisKey"+str;//生成唯一的redis的Key值
        redisTemplate.opsForValue().set(redisKey,str,120, TimeUnit.SECONDS);//将验证码数据添加到redis中,失效时间是1分钟
        logger.debug("UtilController generateCaptchaImageAndStoreKey returnValue:"+redisKey);
    }
}
