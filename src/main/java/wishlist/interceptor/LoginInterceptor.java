package wishlist.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if(session == null){
            response.sendRedirect("/auth/login");
            return false;
        }

        Integer memberId = (Integer) session.getAttribute("memberId");

        if (memberId == null){
            response.sendRedirect("/auth/login");
            return false;
        }

        return true;
    }
}
