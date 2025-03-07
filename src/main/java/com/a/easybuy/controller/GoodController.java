package com.a.easybuy.controller;

import com.a.easybuy.pojo.Good;
import com.a.easybuy.pojo.GoodsQuery;
import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.service.GoodService;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Controller
@RequestMapping("good")
public class GoodController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodService goodService;

    @RequestMapping("getPage")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage getPage(Integer pageNow,Integer pageSize, @RequestParam(required = false) Integer kid) {
        logger.info("goodService.getPage pageNow:"+pageNow+",pageSize:"+pageSize+",kid"+kid);
        GoodsQuery goodQuery = new GoodsQuery();
        if (kid!=null){
            goodQuery.setKid(kid);
        }
        ResponseMessage responseMessage =goodService.getGoodsByPage(goodQuery,pageNow,pageSize);
        logger.debug("goodService.getPage responseMessage:"+responseMessage+"goodQuery:"+goodQuery);
        return responseMessage;
    }
    @RequestMapping("update")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage updateGood(Good good) {
        logger.info("goodService.updateGood"+good);
        ResponseMessage responseMessage =goodService.updateGood(good);
        logger.debug("goodService.updateGood"+responseMessage);
        return responseMessage;
    }

    @RequestMapping("add")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage addGood(Good good,@RequestParam(value = "file", required = false) MultipartFile mf)  throws IllegalStateException, IOException {
        logger.info("goodService.addGood"+good);
        if (!mf.isEmpty()) {
            String fileName = mf.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            String[] fileExtensionArr = { "jpg", "gif", "png" };
            boolean isOk = false;
            for (String fileExtension : fileExtensionArr) {
                if (fileExtension.equals(extension)) {
                    isOk = true;
                    break;
                }
            }
            if (!isOk) {
                ResponseMessage responseMessage = new ResponseMessage();
                responseMessage.setCode("300");
                return responseMessage;
            }
            String newFileName = UUID.randomUUID() + "." + extension;
            good.setImgPath(newFileName);
            mf.transferTo(new File("D:" + File.separator + "image" + File.separator + newFileName));
        }
        ResponseMessage responseMessage =goodService.addGood(good);
        logger.debug("goodService.addGood"+responseMessage);
        return responseMessage;
    }

    @RequestMapping("del")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage delGood(Integer id) {
        logger.info("goodService.delGood"+id);
        ResponseMessage responseMessage = goodService.deleteGood(id);
        logger.debug("goodService.delGood"+responseMessage);
        return responseMessage;
    }
    @RequestMapping("getGood")
    @ResponseBody
    @CrossOrigin
    public ResponseMessage getGood(Integer id) {
        logger.info("goodService.getGood"+id);
        ResponseMessage responseMessage = goodService.getGood(id);
        logger.debug("goodService.getGood"+responseMessage);
        return responseMessage;
    }

    @RequestMapping(value = "downloadFile")
    public void downloadFile(String picPath, HttpServletResponse resp) throws IOException {
        if(StringUtils.isNotBlank(picPath)) {
            File file = new File("D:\\image\\" + picPath);
            if(file.exists()) {
                InputStream is = new FileInputStream(file);
                ServletOutputStream outputStream = resp.getOutputStream();
                byte b[] = new byte[1024];
                int n;
                while((n=is.read(b))!=-1) {
                    outputStream.write(b,0,n);
                }
                outputStream.close();
                is.close();
            }
        }
    }
}
