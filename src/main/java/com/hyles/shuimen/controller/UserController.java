package com.hyles.shuimen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyles.shuimen.common.R;
import com.hyles.shuimen.entity.User;
import com.hyles.shuimen.service.UserService;
import com.hyles.shuimen.utils.MailComponent;
import com.hyles.shuimen.utils.SMSUtils;
import com.hyles.shuimen.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailComponent mailComponent;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession httpSession){
        //1. 获取手机号/邮箱号
        String number = user.getPhone();

        if(StringUtils.isNotEmpty(number)){
            // 生成4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code = {}",code);

            // 调用阿里云提供的短信服务
            // SMSUtils.sendMessage("xxx","",number,code);
            // 发送邮件
            mailComponent.send(number,code);

            // 将手机号/邮箱号 存入到 session中，为了后面登录验证
            httpSession.setAttribute(number,code);

            return R.success("发送验证码-成功");
        }
        return R.error("验证码-发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map user,HttpSession httpSession){
        // 获取表单信息
        String number = user.get("phone").toString();
        String code = user.get("code").toString();

        Object realCode = httpSession.getAttribute(number);
        if(realCode != null && realCode.equals(code)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,number);

            User user1 = userService.getOne(queryWrapper);
            if(user1 == null){
                // 新用户
                user1 = new User();
                user1.setPhone(number);
                user1.setStatus(1);
                userService.save(user1);
            }
            httpSession.setAttribute("user",user1.getId());
            return R.success(user1);
        }
        return R.error("登录失败");

    }
    // 如果用户第一次登录使用验证码，后面直接进入，如果超过一段时间再登录，又需要验证码

}
