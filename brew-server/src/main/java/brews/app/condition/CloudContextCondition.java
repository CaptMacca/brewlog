package brews.app.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * CloudContextCondition for alternative configuration setup when deployed to cloud env ie secrets storage in alternative
 * more secure areas (AWS Secrets/Param store, Azure Param Store)
 */
public class CloudContextCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return !(new LocalContextCondition().matches(context, metadata));
    }
}
