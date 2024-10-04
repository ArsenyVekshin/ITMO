package com.arsenyvekshin.lab1_backend.filters;

import com.arsenyvekshin.lab1_backend.entities.AuthToken;
import com.arsenyvekshin.lab1_backend.repositories.AuthTokenRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class TokenAuthFilter implements Filter {

    @Autowired
    private AuthTokenRepository authTokenRepository;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Обработка запроса
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        AuthToken token = authTokenRepository.findByToken(httpRequest.getHeader("auth-token"));

        if (token == null)
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing token");
        else if (token.isExpired())
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Auth token expired");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

