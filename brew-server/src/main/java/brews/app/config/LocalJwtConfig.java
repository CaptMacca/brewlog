package brews.app.config;

import brews.app.condition.LocalContextCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * Local profile context implementation that retrieves jwt config from local properties
 */
@Component
@Conditional(LocalContextCondition.class)
public class LocalJwtConfig implements JwtConfig {

    @Value("${brews.app.jwtSecret}")
    private String jwtSecret;

    @Value("${brews.app.jwtExpiration}")
    private Integer jwtExpiration;

    public String getJwtSecretKey() {
        return this.jwtSecret;
    }

    public Integer getJwtExpiration() {
        return this.jwtExpiration;
    }
}
