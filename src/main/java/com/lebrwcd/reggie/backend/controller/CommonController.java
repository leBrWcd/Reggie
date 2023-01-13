package com.lebrwcd.reggie.backend.controller;/**
 * @author lebrwcd
 * @date 2023/1/12
 * @note
 */

import com.lebrwcd.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

/**
 * ClassName CommonController
 * Description 负责文件上传和下载
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/12
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.uploadPath}")
    private String uploadPath;


    /**
     * 上传图片
     *
     * @param file 上传的文件
     * @return 图片名称
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        // file是一个临时文件，需要转存到其他指定路径，不然程序结束，file也消失
        log.info("file信息：{}", file.toString());
        // 转存到指定位置
        String originalName = file.getOriginalFilename();
        int index = originalName.lastIndexOf(".");
        String suffix = originalName.substring(index);
        // UUID随机
        String fileName = UUID.randomUUID().toString() + suffix;
        // 创建一个目录对象
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            // 创建
            dir.mkdirs();
        }
        try {
            //file.transferTo(new File(uploadPath + fileName + "." + originalName.split("\\.")[1]));
            file.transferTo(new File(uploadPath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    /**
     * 该方法在图片上传成功后调用，将图片地址以流的方式写回给浏览器，供浏览器展示图片
     * <p>
     * 图片下载
     *
     * @param name 上传后返回的图片名称
     * @Param response 响应
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        log.info("==================正在下载图片===================: {}",name);
        try {
            // 通过输入流的方式读取文件内容
            FileInputStream inputStream = new FileInputStream(new File(uploadPath + name));
            // 设置响应内容
            response.setContentType("image/jpeg");
            // 通过输出流的方式写回给浏览器
            ServletOutputStream outputStream = response.getOutputStream();
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes,0,len);
                // 刷新缓存
                outputStream.flush();
            }
            // 清除流
            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
