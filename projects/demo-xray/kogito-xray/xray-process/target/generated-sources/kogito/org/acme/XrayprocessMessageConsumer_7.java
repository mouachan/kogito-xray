/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.acme;

import io.smallrye.mutiny.Multi;
import org.kie.kogito.Application;
import org.kie.kogito.conf.ConfigBean;
import org.kie.kogito.event.KogitoEventStreams;
import org.kie.kogito.event.impl.DefaultEventConsumerFactory;
import org.kie.kogito.process.Process;
import org.kie.kogito.services.event.impl.AbstractMessageConsumer;
import org.reactivestreams.Publisher;

@io.quarkus.runtime.Startup
public class XrayprocessMessageConsumer_7 extends AbstractMessageConsumer<XrayprocessModel, org.acme.XrayEvent, XrayprocessMessageDataEvent_7> {

    @javax.inject.Inject
    Application application;

    @javax.inject.Inject
    @javax.inject.Named("xrayprocess")
    Process<XrayprocessModel> process;

    @javax.inject.Inject
    ConfigBean configBean;

    @javax.inject.Inject
    @javax.inject.Named(KogitoEventStreams.PUBLISHER)
    Publisher<String> eventPublisher;

    @javax.annotation.PostConstruct
    void init() {
        setParams(application, process, org.acme.XrayEvent.class, XrayprocessMessageDataEvent_7.class, "xrayevent", new DefaultEventConsumerFactory(), configBean.useCloudEvents());
        Multi.createFrom().publisher(eventPublisher).subscribe().with(this::consume);
    }

    protected XrayprocessModel eventToModel(org.acme.XrayEvent event) {
        XrayprocessModel model = new XrayprocessModel();
        model.setXrayEvent(event);
        return model;
    }
}
