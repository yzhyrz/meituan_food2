package com.hyles.shuimen.controller;

import com.hyles.shuimen.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    /**
     * 将配置文件中名为 shuimen.path 的属性值注入到 basePath 字段中
     */
    @Value("${shuimen.path}")
    private String basePath;


    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        log.info(file.toString());

        //获取文件的原始名
        String originalName = file.getOriginalFilename();//abc.jpg
        String suffix = originalName.substring(originalName.lastIndexOf('.'));//jpg
        //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;//xx.jpg

        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdir();
        }

        try {
            // 将临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        FileInputStream is = null;
        ServletOutputStream outputStream = null;
        try {
            // 通过输入流读取文件内容
            is = new FileInputStream(new File(basePath+name));

            // 输出流，通过输出流将文件写回浏览器，在浏览器展开图片
            outputStream = response.getOutputStream();

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = is.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                outputStream.close();
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
