package com.fsd2020.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class PreFilter extends ZuulFilter {

	private static final Logger logger = LoggerFactory.getLogger(PreFilter.class);

	@Autowired
	TokenValidation tokenValidation;
	
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run(){
        //获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取Request
        HttpServletRequest request = ctx.getRequest();
        //获取请求参数accessToken
        String accessToken = request.getHeader("accessToken");

        logger.info(request.getRequestURI());
        if ("authservice".equals(request.getRequestURI().replace("/",""))){
            logger.info("login service - 放行");
            return null;
        }

        //使用String工具类
        if (StringUtils.isBlank(accessToken)) {
            logger.error("no token");
            logger.warn("accessToken is empty");
            ctx.setSendZuulResponse(false);  //进行拦截
            ctx.setResponseStatusCode(401);

            try {
                ctx.getResponse().getWriter().write("{\"status\": \"401\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        
        tokenValidation.validateToken(accessToken);

        // token exist
        logger.info("access is ok");
        return null;
    }
}
