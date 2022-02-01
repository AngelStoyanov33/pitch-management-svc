package me.angelstoyanov.sporton.management.pitch.config;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@StaticInitSafe
@ApplicationScoped
@RegisterForReflection
@ConfigMapping(prefix = "sporton.osmapi")
public interface OSMAPIConfig {

    @WithName("servers.active.list")
    List<String> getActiveServersList();

    @WithName("servers.active.instance")
    int getActiveServerIndex();

    @WithName("user.agent")
    String getUserAgent();

    @WithName("request.timeout")
    int getRequestTimeout();
}
