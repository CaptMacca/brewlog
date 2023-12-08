package brews.app.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;

public class LocalContextCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Arrays.stream(context.getEnvironment().getActiveProfiles()).anyMatch(
                profile -> (profile.equalsIgnoreCase("local") || profile.equalsIgnoreCase("test") || profile.equalsIgnoreCase("IT")));
    }
}