package brews.app.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;

/**
 * LocalContextCondition implements a conditional when local profiles are used. Can be used to control configuration.
 * For example: retrieval of secrets in less secure manner via properties
 */
public class LocalContextCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        return Arrays.stream(env.getActiveProfiles())
                     .anyMatch(profile -> profile.startsWith("local") ||
                                          profile.startsWith("test"));
    }
}
