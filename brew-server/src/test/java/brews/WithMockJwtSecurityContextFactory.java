package brews;

import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Map;

public class WithMockJwtSecurityContextFactory
//  implements WithSecurityContextFactory<WithMockJwt>
{
//    @Override
//    public SecurityContext createSecurityContext(WithMockJwt annotation) {
//        JWT jwt = Jwt.withTokenValue("token")
//          .header("alg", "none")
//          .claim("sub", annotation.value())
//          .claim("user", Map.of("email", annotation.email()))
//          .build();
//
//        val authorities = AuthorityUtils.createAuthorityList(annotation.roles());
//        val token = new JwtAuthenticationToken(jwt, authorities);
//
//        SecurityContext context = (SecurityContext) SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(token);
//        return context;
//
//    }
}