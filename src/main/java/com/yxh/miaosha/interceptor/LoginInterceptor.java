//package com.yxh.miaosha.interceptor;
//
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author galaxy
// * @date 20-2-15 - 下午12:35
// */
//public class LoginInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        Cookie[] cookies = request.getCookies();
//        if (cookies==null||cookies.length==0){
//            response.sendRedirect("/login/to_login");
//            return false;
//        }
//        if (request.getParameter("token")!=null){
//            return true;
//        }
//        for (Cookie cookie:
//             cookies) {
//            if ("token".equals(cookie.getName())){
//                System.out.println(cookie.getValue());
//                return true;
//            }
//        }
//        response.sendRedirect("/login/to_login");
//        return false;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
//}
