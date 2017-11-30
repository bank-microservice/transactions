package com.rso.bank.transactions.api.v1.health;


import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import com.rso.bank.transactions.api.v1.configuration.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class TransactionServiceHealthCheck implements HealthCheck {

    @Inject
    private RestProperties restProperties;

    @Override
    public HealthCheckResponse call() {

        if (restProperties.isHealthy()) {
            return HealthCheckResponse.named(TransactionServiceHealthCheck.class.getSimpleName()).up().build();
        } else {
            return HealthCheckResponse.named(TransactionServiceHealthCheck.class.getSimpleName()).down().build();
        }

    }

}
