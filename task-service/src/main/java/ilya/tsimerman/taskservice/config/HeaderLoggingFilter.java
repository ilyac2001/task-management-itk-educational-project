package ilya.tsimerman.taskservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class HeaderLoggingFilter extends OncePerRequestFilter {

    private static final String USER_ID_HEADER = "user-id";
    private static final String USER_ROLES_HEADER = "user-roles";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String userId = request.getHeader(USER_ID_HEADER);
        String roles = request.getHeader(USER_ROLES_HEADER);

        log.info("HEADERS: user-id={}, user-roles={}",
                userId,
                roles
        );

        filterChain.doFilter(request, response);
    }
}
