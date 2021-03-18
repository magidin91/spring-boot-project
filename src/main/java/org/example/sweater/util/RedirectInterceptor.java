package org.example.sweater.util;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectInterceptor extends HandlerInterceptorAdapter {
    @Override
    /* после исполнения метода-обработчика, перехватчик выполнит указанные ниже действия */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            /* получаем параметры запроса и урл, чтобы указать страничку, с которой пришли для Turbolinks */
            String args = request.getQueryString() != null ? "?" + request.getQueryString() : "";
            String url = request.getRequestURI() + args;
            /* устанавливаем к Http-заголовкам респонса хедер с именем Turbolinks-Location и значением url */
            response.setHeader("Turbolinks-Location", url);
        }
    }
}
