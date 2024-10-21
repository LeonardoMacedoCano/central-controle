package br.com.lcano.centraldecontrole.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("br.com.lcano.centraldecontrole.resources");
    }
}

