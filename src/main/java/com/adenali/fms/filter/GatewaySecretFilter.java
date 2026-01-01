package com.adenali.fms.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class GatewaySecretFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private  String sharedSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestSecret = request.getHeader("X-Gateway-Secret");
        String userName = request.getHeader("X-User-Id");
        log.info("Gateway Secret: {} shared secret: {}", requestSecret,sharedSecret);
        if (sharedSecret.equals(requestSecret)) {
            log.info("Gateway Secret is valid");
            String finalIdentity = (userName != null) ? userName : "System_Gateway";

            GatewayAuthenticationToken auth = new GatewayAuthenticationToken(finalIdentity);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }else{
            log.error("Invalid Gateway Secret");
        }

        filterChain.doFilter(request, response);
    }
}
