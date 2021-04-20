package org.kie.kogito.app;

import org.kie.kogito.event.CloudEventMeta;

public class CloudEventMetaFactory {

    @javax.enterprise.inject.Produces()
    public CloudEventMeta buildCloudEventMeta_PRODUCED_processedxray() {
        return new CloudEventMeta("process.xrayprocess.processedxray", "/process/xrayprocess", org.kie.kogito.event.EventKind.PRODUCED);
    }

    @javax.enterprise.inject.Produces()
    public CloudEventMeta buildCloudEventMeta_CONSUMED_xrayevent() {
        return new CloudEventMeta("xrayevent", "", org.kie.kogito.event.EventKind.CONSUMED);
    }
}
