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
import java.util.HashMap;
import org.kie.kogito.UserTask;
import org.kie.kogito.UserTaskParam.ParamType;
import org.kie.kogito.UserTaskParam;

@UserTask(taskName = "Radiologist", processName = "xrayprocess")
public class Xrayprocess_6_TaskOutput implements org.kie.kogito.MapOutput {

    public Map<String, Object> toMap() {
        Map<String, Object> params = new HashMap();
        params.put("secondOpinion", this.secondOpinion);
        params.put("comments", this.comments);
        return params;
    }

    public static Xrayprocess_6_TaskOutput fromMap(Map<String, Object> params) {
        org.acme.Xrayprocess_6_TaskOutput result = new Xrayprocess_6_TaskOutput();
        result.secondOpinion = (Boolean) params.get("secondOpinion");
        result.comments = (String) params.get("comments");
        return result;
    }

    @UserTaskParam(value = ParamType.OUTPUT)
    private Boolean secondOpinion;

    public Boolean getSecondOpinion() {
        return secondOpinion;
    }

    public void setSecondOpinion(Boolean secondOpinion) {
        this.secondOpinion = secondOpinion;
    }

    @UserTaskParam(value = ParamType.OUTPUT)
    private String comments;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
// Task output model for user task 'Radiologist' in process 'xrayprocess'
