package org.openmrs.sync.remote.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ScheduledRoute extends RouteBuilder {

    private static final int DELAY = 60 * 1000;

    @Override
    public void configure() {
        from("scheduler:sync?delay=" + DELAY)
                .to("jpa://org.openmrs.sync.remote.management.entity.EntitySyncStatus?" +
                        "query=select p from org.openmrs.sync.remote.management.entity.EntitySyncStatus p")
                .split(body()).streaming()
                .setHeader("OpenMrsEntitySyncStatusId", simple("${in.body.getId()}"))
                .setHeader("OpenMrsEntitySyncName", simple("${in.body.getEntityName().name()}"))
                .to("seda:sync");
    }
}
