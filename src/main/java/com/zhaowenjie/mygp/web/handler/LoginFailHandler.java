package com.zhaowenjie.mygp.web.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhaowenjie.mygp.config.MessageUtil;
import com.zhaowenjie.mygp.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailHandler implements AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        System.out.println("到錯了");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.getWriter().write(objectMapper.writeValueAsString(MessageUtil.error(CodeUtil.LOGINFAIL_CODE,"登陆失败：" + exception.getMessage())));
    }
}
