package com.zhaowenjie.mygp.web.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhaowenjie.mygp.config.MessageUtil;
import com.zhaowenjie.mygp.utils.CodeUtil;
import com.zhaowenjie.mygp.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    @Qualifier("userDetailServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        try {
            User details = (User) userDetailsService.loadUserByUsername(authentication.getName());
            String token = JwtTokenUtils.TOKEN_PREFIX + JwtTokenUtils.createToken(details, false);
            response.setHeader(JwtTokenUtils.TOKEN_HEADER, token);
            List<Object> list = new ArrayList<>();
            list.add(token);
            list.add(details.getAuthorities());
            response.getWriter().write(objectMapper.writeValueAsString(MessageUtil.success(list)));
        } catch (Exception e) {
            response.getWriter().write(objectMapper.writeValueAsString(MessageUtil.error(CodeUtil.LOGINFAIL_CODE, "创建token失败，请与管理员联系")));
        }

    }
}
