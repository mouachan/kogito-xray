/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
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

import java.util.Map;
import org.kie.kogito.UserTask;

@UserTask(taskName = "Radiologist", processName = "xrayprocess")
public class Xrayprocess_6_TaskInput {

    private String _id;

    private String _name;

    public void setId(String id) {
        this._id = id;
    }

    public String getId() {
        return this._id;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getName() {
        return this._name;
    }

    public static Xrayprocess_6_TaskInput from(org.kie.kogito.process.WorkItem workItem) {
        Xrayprocess_6_TaskInput item = new Xrayprocess_6_TaskInput();
        item._id = workItem.getId();
        item._name = workItem.getName();
        Map<String, Object> params = workItem.getParameters();
        return item;
    }
}
// Task input model for user task 'Radiologist' in process 'xrayprocess'
