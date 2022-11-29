package he4rt.open.tibia.world.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

@AllArgsConstructor
@Getter
@Configuration
public class IntegrationConfig {

    private final TaskScheduler taskScheduler;

}
