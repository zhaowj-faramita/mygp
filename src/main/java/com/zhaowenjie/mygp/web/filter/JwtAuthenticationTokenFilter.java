package com.zhaowenjie.mygp.web.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhaowenjie.mygp.config.MessageUtil;
import com.zhaowenjie.mygp.utils.CodeUtil;
import com.zhaowenjie.mygp.utils.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("userDetailServiceImpl")
    private UserDetailsService userDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(JwtTokenUtils.TOKEN_HEADER);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        String allowHeaders = "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, Authorization";
        response.addHeader("Access-Control-Allow-Headers", allowHeaders);
        if (request.getMethod().equals( "OPTIONS" )) {
            response.setStatus( 200 );
            return;
        }
        if(token != null && StringUtils.startsWith(token, JwtTokenUtils.TOKEN_PREFIX)) {
            token = StringUtils.substring(token, JwtTokenUtils.TOKEN_PREFIX.length());
        } else {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String username = JwtTokenUtils.getUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                /*
                 *  注意：
                 *       这里代码不应该从数据库中去查，而是从缓存中根据token去查，目前只是做测试，无关紧要
                 *      如果是真正的项目实际开发需要增加缓存
                 */
                UserDetails userDetails = userDetailService.loadUserByUsername(username);

                if (JwtTokenUtils.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetails(request));


                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        } catch (Exception e) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(MessageUtil.error(CodeUtil.LOGINFAIL_CODE,"令牌已失效")));
            return;
        }
        filterChain.doFilter(request, response);
    }

}
