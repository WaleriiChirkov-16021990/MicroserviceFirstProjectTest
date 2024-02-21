package ru.chirkov.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.chirkov.aspect.ExecutionForReturnObject;
import ru.chirkov.aspect.TimerAspect;

@Configuration
public class CusConfigurer {

    @Bean
    TimerAspect timerAspect() {
        return new TimerAspect();
    }

//    @Bean
//    @ConditionalOnProperty(name = "cus.timer.execute.for.return.object", havingValue = "enable")
//    ExecutionForReturnObject executionForReturnObject() {
//        return new ExecutionForReturnObject();
//    }
}
