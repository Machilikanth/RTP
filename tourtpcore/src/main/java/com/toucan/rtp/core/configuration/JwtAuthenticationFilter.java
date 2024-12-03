//package com.toucan.rtp.core.configuration;
//
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.toucan.rtp.core.service.JwtService;
//import com.toucan.rtp.core.service.UserService;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Configuration
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtService jwtService;
//    @Autowired
//    private UserService myUserDetailService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String jwt = authHeader.substring(7);
//        //String username = jwtService.extractUsername(jwt);
//        String phone=jwtService.extractMobileNumber(jwt);
//		if (/* username != null && */SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = myUserDetailService.loadUserByMobileNumber(phone);
//            System.out.println("the password is "+userDetails.getPassword()+""+userDetails.getAuthorities());
//            if (userDetails != null && jwtService.isTokenValid(jwt)) {
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
////                        username,
//                		userDetails,
//                        null,
//                        null
//                );
//               
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
////                System.out.println("qqqqqqqqq->"+authenticationToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }


