/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
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

@org.kie.kogito.codegen.Generated(value = "kogito-codegen", reference = "xrayprocess", name = "Xrayprocess", hidden = true)
public class XrayprocessModelInput implements org.kie.kogito.Model {

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> params = new HashMap();
        params.put("patientName", this.patientName);
        params.put("dateAcquired", this.dateAcquired);
        params.put("patientID", this.patientID);
        params.put("dateOfBirth", this.dateOfBirth);
        params.put("xrayEvent", this.xrayEvent);
        return params;
    }

    @Override
    public void fromMap(Map<String, Object> params) {
        fromMap(null, params);
    }

    @Override
    public void update(Map<String, Object> params) {
        fromMap(params);
    }

    public void fromMap(String id, Map<String, Object> params) {
        this.patientName = (java.lang.String) params.get("patientName");
        this.dateAcquired = (java.lang.String) params.get("dateAcquired");
        this.patientID = (java.lang.String) params.get("patientID");
        this.dateOfBirth = (java.lang.String) params.get("dateOfBirth");
        this.xrayEvent = (org.acme.XrayEvent) params.get("xrayEvent");
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "patientName")
    @javax.validation.Valid()
    private java.lang.String patientName;

    public java.lang.String getPatientName() {
        return patientName;
    }

    public void setPatientName(java.lang.String patientName) {
        this.patientName = patientName;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "dateAcquired")
    @javax.validation.Valid()
    private java.lang.String dateAcquired;

    public java.lang.String getDateAcquired() {
        return dateAcquired;
    }

    public void setDateAcquired(java.lang.String dateAcquired) {
        this.dateAcquired = dateAcquired;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "patientID")
    @javax.validation.Valid()
    private java.lang.String patientID;

    public java.lang.String getPatientID() {
        return patientID;
    }

    public void setPatientID(java.lang.String patientID) {
        this.patientID = patientID;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "dateOfBirth")
    @javax.validation.Valid()
    private java.lang.String dateOfBirth;

    public java.lang.String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(java.lang.String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "xrayEvent")
    @javax.validation.Valid()
    private org.acme.XrayEvent xrayEvent;

    public org.acme.XrayEvent getXrayEvent() {
        return xrayEvent;
    }

    public void setXrayEvent(org.acme.XrayEvent xrayEvent) {
        this.xrayEvent = xrayEvent;
    }

    public XrayprocessModel toModel() {
        XrayprocessModel result = new XrayprocessModel();
        result.setPatientName(getPatientName());
        result.setDateAcquired(getDateAcquired());
        result.setPatientID(getPatientID());
        result.setDateOfBirth(getDateOfBirth());
        result.setXrayEvent(getXrayEvent());
        return result;
    }
}
