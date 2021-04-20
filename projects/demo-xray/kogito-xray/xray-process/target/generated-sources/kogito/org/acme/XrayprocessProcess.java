package org.acme;

import org.jbpm.process.core.datatype.impl.type.ObjectDataType;
import org.jbpm.ruleflow.core.RuleFlowProcessFactory;
import org.drools.core.util.KieFunctions;

@javax.enterprise.context.ApplicationScoped()
@javax.inject.Named("xrayprocess")
@io.quarkus.runtime.Startup()
public class XrayprocessProcess extends org.kie.kogito.process.impl.AbstractProcess<org.acme.XrayprocessModel> {

    @javax.inject.Inject()
    org.acme.XrayprocessMessageProducer_1 producer_1;

    @javax.inject.Inject()
    public XrayprocessProcess(org.kie.kogito.app.Application app, org.kie.kogito.process.ProcessInstancesFactory factory) {
        super(app, java.util.Arrays.asList(), factory);
        activate();
    }

    public XrayprocessProcess() {
    }

    @Override()
    public org.acme.XrayprocessProcessInstance createInstance(org.acme.XrayprocessModel value) {
        return new org.acme.XrayprocessProcessInstance(this, value, this.createProcessRuntime());
    }

    public org.acme.XrayprocessProcessInstance createInstance(java.lang.String businessKey, org.acme.XrayprocessModel value) {
        return new org.acme.XrayprocessProcessInstance(this, value, businessKey, this.createProcessRuntime());
    }

    @Override()
    public org.acme.XrayprocessModel createModel() {
        return new org.acme.XrayprocessModel();
    }

    public org.acme.XrayprocessProcessInstance createInstance(org.kie.kogito.Model value) {
        return this.createInstance((org.acme.XrayprocessModel) value);
    }

    public org.acme.XrayprocessProcessInstance createInstance(java.lang.String businessKey, org.kie.kogito.Model value) {
        return this.createInstance(businessKey, (org.acme.XrayprocessModel) value);
    }

    public org.acme.XrayprocessProcessInstance createInstance(org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        return new org.acme.XrayprocessProcessInstance(this, this.createModel(), this.createProcessRuntime(), wpi);
    }

    public org.acme.XrayprocessProcessInstance createReadOnlyInstance(org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        return new org.acme.XrayprocessProcessInstance(this, this.createModel(), wpi);
    }

    public org.kie.api.definition.process.Process process() {
        RuleFlowProcessFactory factory = RuleFlowProcessFactory.createProcess("xrayprocess");
        factory.variable("xrayEvent", new ObjectDataType("org.acme.XrayEvent"), "customTags", null);
        factory.variable("patientName", new ObjectDataType("java.lang.String"), "customTags", null);
        factory.variable("patientID", new ObjectDataType("java.lang.String"), "customTags", null);
        factory.variable("dateOfBirth", new ObjectDataType("java.lang.String"), "customTags", null);
        factory.variable("dateAcquired", new ObjectDataType("java.lang.String"), "customTags", null);
        factory.name("xray-process");
        factory.packageName("org.acme");
        factory.dynamic(false);
        factory.version("1.0");
        factory.visibility("Public");
        factory.metaData("elementname", "Low confidence x-ray images are evaluated by a radiologist, who determines Yes, No or Second Opinion on a pneumonia diagnosis");
        factory.metaData("TargetNamespace", "http://www.omg.org/bpmn20");
        org.jbpm.ruleflow.core.factory.EndNodeFactory endNode1 = factory.endNode(1);
        endNode1.name("End");
        endNode1.terminate(false);
        endNode1.action(kcontext -> {
            org.acme.XrayEvent object = (org.acme.XrayEvent) kcontext.getVariable("xrayEvent");
            org.drools.core.common.InternalKnowledgeRuntime runtime = (org.drools.core.common.InternalKnowledgeRuntime) kcontext.getKieRuntime();
            org.kie.kogito.internal.process.runtime.KogitoProcessInstance pi = (org.kie.kogito.internal.process.runtime.KogitoProcessInstance) kcontext.getProcessInstance();
            org.jbpm.process.instance.InternalProcessRuntime process = (org.jbpm.process.instance.InternalProcessRuntime) runtime.getProcessRuntime();
            process.getProcessEventSupport().fireOnMessage(pi, kcontext.getNodeInstance(), runtime, "processedxray", object);
            producer_1.produce(pi, object);
        });
        endNode1.metaData("UniqueId", "_5EC0E2A9-BDC9-4E11-82B3-2DBEDAEC3C40");
        endNode1.metaData("TriggerType", "ProduceMessage");
        endNode1.metaData("x", 892);
        endNode1.metaData("width", 56);
        endNode1.metaData("y", 198);
        endNode1.metaData("TriggerRef", "processedxray");
        endNode1.metaData("MappingVariable", "xrayEvent");
        endNode1.metaData("MessageType", "org.acme.XrayEvent");
        endNode1.metaData("height", 56);
        endNode1.done();
        org.jbpm.ruleflow.core.factory.JoinFactory joinNode2 = factory.joinNode(2);
        joinNode2.name("Join");
        joinNode2.type(2);
        joinNode2.metaData("UniqueId", "_23EF52C0-BD0B-4C5B-BD57-BA9C57100C96");
        joinNode2.metaData("x", 762);
        joinNode2.metaData("width", 56);
        joinNode2.metaData("y", 198);
        joinNode2.metaData("height", 56);
        joinNode2.done();
        org.jbpm.ruleflow.core.factory.ActionNodeFactory actionNode3 = factory.actionNode(3);
        actionNode3.name("Extract Payload");
        actionNode3.action(kcontext -> {
            org.acme.XrayEvent xrayEvent = (org.acme.XrayEvent) kcontext.getVariable("xrayEvent");
            java.lang.String patientName = (java.lang.String) kcontext.getVariable("patientName");
            java.lang.String patientID = (java.lang.String) kcontext.getVariable("patientID");
            java.lang.String dateOfBirth = (java.lang.String) kcontext.getVariable("dateOfBirth");
            java.lang.String dateAcquired = (java.lang.String) kcontext.getVariable("dateAcquired");
            String[] values = xrayEvent.getKey().split("_");
            if (values.length > 4) {
                kcontext.setVariable("patientName", values[1]);
                kcontext.setVariable("patientID", values[2]);
                kcontext.setVariable("dateOfBirth", values[3]);
                kcontext.setVariable("dateAcquired", values[4].substring(0, values[4].indexOf('.')));
            }
        });
        actionNode3.metaData("UniqueId", "_5F7E0EDE-8AA6-46F5-B2E7-6EC626325C2B");
        actionNode3.metaData("elementname", "Extract Payload");
        actionNode3.metaData("NodeType", "ScriptTask");
        actionNode3.metaData("x", 218);
        actionNode3.metaData("width", 131);
        actionNode3.metaData("y", 186);
        actionNode3.metaData("height", 80);
        actionNode3.done();
        org.jbpm.ruleflow.core.factory.HumanTaskNodeFactory humanTaskNode4 = factory.humanTaskNode(4);
        humanTaskNode4.name("Second Opinion");
        humanTaskNode4.workParameter("TaskName", "Task");
        humanTaskNode4.workParameter("Skippable", "false");
        humanTaskNode4.workParameter("NodeName", "Second Opinion");
        humanTaskNode4.done();
        humanTaskNode4.metaData("UniqueId", "_1D3614CC-9ABF-4D20-A2E7-DCBE2728AE2E");
        humanTaskNode4.metaData("elementname", "Second Opinion");
        humanTaskNode4.metaData("x", 589);
        humanTaskNode4.metaData("width", 154);
        humanTaskNode4.metaData("y", 362);
        humanTaskNode4.metaData("height", 102);
        org.jbpm.ruleflow.core.factory.SplitFactory splitNode5 = factory.splitNode(5);
        splitNode5.name("Second Opinion?");
        splitNode5.type(2);
        splitNode5.metaData("UniqueId", "_97356201-9EB5-48CE-8428-97E8E03F8445");
        splitNode5.metaData("elementname", "Second Opinion?");
        splitNode5.metaData("x", 638);
        splitNode5.metaData("width", 56);
        splitNode5.metaData("y", 198);
        splitNode5.metaData("Default", "_76AEEF0F-5A44-430D-A733-C568D1CFE7D1");
        splitNode5.metaData("height", 56);
        splitNode5.constraint(4, "_90F9E54D-72E0-4912-A115-DA8FB14EC9B8", "DROOLS_DEFAULT", "java", kcontext -> {
            org.acme.XrayEvent xrayEvent = (org.acme.XrayEvent) kcontext.getVariable("xrayEvent");
            java.lang.String patientName = (java.lang.String) kcontext.getVariable("patientName");
            java.lang.String patientID = (java.lang.String) kcontext.getVariable("patientID");
            java.lang.String dateOfBirth = (java.lang.String) kcontext.getVariable("dateOfBirth");
            java.lang.String dateAcquired = (java.lang.String) kcontext.getVariable("dateAcquired");
            return xrayEvent.getSecondOpinion();
        }, 0);
        splitNode5.constraint(2, "_76AEEF0F-5A44-430D-A733-C568D1CFE7D1", "DROOLS_DEFAULT", "java", kcontext -> {
            org.acme.XrayEvent xrayEvent = (org.acme.XrayEvent) kcontext.getVariable("xrayEvent");
            java.lang.String patientName = (java.lang.String) kcontext.getVariable("patientName");
            java.lang.String patientID = (java.lang.String) kcontext.getVariable("patientID");
            java.lang.String dateOfBirth = (java.lang.String) kcontext.getVariable("dateOfBirth");
            java.lang.String dateAcquired = (java.lang.String) kcontext.getVariable("dateAcquired");
            return !xrayEvent.getSecondOpinion();
        }, 0);
        splitNode5.done();
        org.jbpm.ruleflow.core.factory.HumanTaskNodeFactory humanTaskNode6 = factory.humanTaskNode(6);
        humanTaskNode6.name("Radiologist");
        humanTaskNode6.workParameter("TaskName", "Radiologist");
        humanTaskNode6.workParameter("Skippable", "false");
        humanTaskNode6.workParameter("NodeName", "Radiologist");
        humanTaskNode6.outMapping("secondOpinion", "_90DD009A-DEFA-4E1A-9461-779C54FA60DA_secondOpinionOutputX#{xrayEvent.secondOpinion}");
        humanTaskNode6.outMapping("comments", "_90DD009A-DEFA-4E1A-9461-779C54FA60DA_commentsOutputX#{xrayEvent.comments}");
        humanTaskNode6.done();
        humanTaskNode6.metaData("UniqueId", "_90DD009A-DEFA-4E1A-9461-779C54FA60DA");
        humanTaskNode6.metaData("elementname", "Radiologist");
        humanTaskNode6.metaData("x", 408);
        humanTaskNode6.metaData("width", 154);
        humanTaskNode6.metaData("y", 175);
        humanTaskNode6.metaData("height", 102);
        org.jbpm.ruleflow.core.factory.StartNodeFactory startNode7 = factory.startNode(7);
        startNode7.name("Inbound Xray");
        startNode7.interrupting(true);
        startNode7.metaData("TriggerMapping", "xrayEvent");
        startNode7.metaData("UniqueId", "_75AC8C0C-CFBD-4ADF-A3B4-83AB90581A73");
        startNode7.metaData("elementname", "Inbound Xray");
        startNode7.metaData("TriggerType", "ConsumeMessage");
        startNode7.metaData("x", 105);
        startNode7.metaData("width", 56);
        startNode7.metaData("y", 198);
        startNode7.metaData("TriggerRef", "xrayevent");
        startNode7.metaData("MessageType", "org.acme.XrayEvent");
        startNode7.metaData("height", 56);
        startNode7.done();
        startNode7.trigger("xrayevent", "xrayEvent", "event");
        factory.connection(2, 1, "_32A124B0-9549-47F1-AA3A-B3CF4F04AC3F");
        factory.connection(4, 2, "_69BACB39-40A4-42C7-BE65-15E7C7198179");
        factory.connection(5, 2, "_76AEEF0F-5A44-430D-A733-C568D1CFE7D1");
        factory.connection(7, 3, "_642950D6-2D67-4303-841E-248B36C4DF03");
        factory.connection(5, 4, "_90F9E54D-72E0-4912-A115-DA8FB14EC9B8");
        factory.connection(6, 5, "_C5E67E3E-2E03-404C-A28C-56AFA69EDE97");
        factory.connection(3, 6, "_4B525D93-5529-4B24-9534-2F19C56401A6");
        factory.validate();
        return factory.getProcess();
    }
}
