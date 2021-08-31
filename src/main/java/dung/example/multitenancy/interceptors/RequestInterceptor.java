package dung.example.multitenancy.interceptors;

import dung.example.multitenancy.tenants.TenantContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws IOException {
        System.out.println("Intercepting the request");
        String tenantId = request.getHeader("X-TenantID");
        System.out.println("TenantId :: " + tenantId);

        if (tenantId == null) {
            response.getWriter().write("X-TenantID not present in request header");
            response.setStatus(400);
            return false;
        }

        TenantContext.setCurrentTenant(tenantId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {
        TenantContext.clear();
    }
}
