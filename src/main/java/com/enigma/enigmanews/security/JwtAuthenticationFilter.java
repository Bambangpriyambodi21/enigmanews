package com.enigma.enigmanews.security;

import com.enigma.enigmanews.model.JwtClaim;
import com.enigma.enigmanews.service.UserService;
import jakarta.persistence.Column;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
            try {
                // validasi token
                String token = parseJwt(request);

                if (token != null && jwtUtils.verifyJwtToken(token)) {
                    JwtClaim userInfo = jwtUtils.getUserInfoByToken(token);

                    UserDetails userDetails = userService.loadByUserId(userInfo.getUserId());

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // menyimpan informasi tambahan berupa: ip.address, web browser
                    authentication.setDetails(new WebAuthenticationDetails(request));
                    // simpan sesi user ke database security context holder
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                log.error("Cannot set user authentication: {}", e.getMessage());
            }

            // melanjutkan ke controller
            filterChain.doFilter(request, response);
        }

        private String parseJwt(HttpServletRequest request) {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                return token.substring(7);
            }
            return null;
        }

}
