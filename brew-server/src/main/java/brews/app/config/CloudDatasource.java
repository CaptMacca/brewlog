package brews.app.config;

import brews.app.condition.CloudContextCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Placeholder for cloud aware datasource configuration. Allows implementation of
 * datasource creation using credentials stored in a more secure cloud service apis.
 * ie. AWS Secrets, Azure Key Vault
 */
@Configuration
@Conditional(CloudContextCondition.class)
public class CloudDatasource {
    @Bean
    DataSource dataSource() {
        return null;
    }
}
