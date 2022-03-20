package brews.app.config;

import brews.app.condition.CloudContextCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * Placeholder class for cloud aware jwt token config from a more secure cloud based
 * service ie AWS Secrets, Azure Key Vault.
 */
@Component
@Conditional(CloudContextCondition.class)
public class CloudJwtConfig implements JwtConfig {

    @Override
    public String getJwtSecretKey() {
        return null;
    }

    public Integer getJwtExpiration() {
        return null;
    }
}
