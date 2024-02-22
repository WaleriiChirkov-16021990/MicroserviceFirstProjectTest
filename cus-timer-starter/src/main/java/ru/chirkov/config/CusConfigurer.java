package ru.chirkov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.chirkov.aspect.TimerAspect;

@Configuration
public class CusConfigurer {
    @Bean
    TimerAspect timerAspect() {
        return new TimerAspect();
    }

}
