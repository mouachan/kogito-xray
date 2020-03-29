
package com.enatndo.config;

import org.entando.config.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class ConfigServiceConfiguration {

    @Bean
    public ConfigService<TrainingConfig> configService(EntandoProperties entandoProperties) {
        return new ConfigService<>(
            entandoProperties.getClientId(),
            entandoProperties.getClientSecret(),
            entandoProperties.getAccessTokenUri(),
            entandoProperties.getConfigServiceUri(),
            TrainingConfig.class);
    }

}