package dmd.test.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
            throws IOException, ServletException {
        res.setStatus(HttpServletResponse.SC_OK);
        for(GrantedAuthority a : auth.getAuthorities()) {
            if ("ADMIN".equals(a.getAuthority())) {
                res.sendRedirect("/admin-dashboard");
            }
        }
    }
    
}
