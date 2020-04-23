package com.zhaowenjie.mygp.web.controller;


import com.zhaowenjie.mygp.config.Message;
import com.zhaowenjie.mygp.config.MessageUtil;
import com.zhaowenjie.mygp.utils.CodeUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/user")
@CrossOrigin
@ApiIgnore
public class SecurityController {
    @GetMapping("/login")
    public Message requireAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return MessageUtil.error(CodeUtil.LOGINFAIL_CODE, "访问的服务需要登录，请前往登录页");
    }
}
