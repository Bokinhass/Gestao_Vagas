package br.com.maciel.vagas.security;

import br.com.maciel.vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private JWTProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException {

    SecurityContextHolder.getContext().setAuthentication(null);

    String header = request.getHeader("Authorization");

    if (request.getRequestURI().startsWith("/company")) {
      if (header != null) {
        var subjecToken = this.jwtProvider.validateToken(header);

        if (subjecToken.isEmpty()) {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          return;
        }
        request.setAttribute("company_id", subjecToken);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subjecToken, null, Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }

    filterChain.doFilter(request, response);
  }
}
