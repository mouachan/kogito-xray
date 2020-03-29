
package com.enatndo.config;

import org.entando.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingConfigManager {

    private final ConfigService<TrainingConfig> configService;

    @Autowired
    public TrainingConfigManager(final ConfigService<TrainingConfig> configService) {
        this.configService = configService;
    }

    public TrainingConfig getTrainingConfig() {
        return Optional.ofNullable(configService.getConfig())
            .orElseGet(TrainingConfig::getDefault);
    }

    public void update(TrainingConfig trainingConfig) {
        configService.updateConfig(trainingConfig);
    }

}

