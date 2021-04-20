package org.acme;

public class XrayprocessProcessInstance extends org.kie.kogito.process.impl.AbstractProcessInstance<XrayprocessModel> {

    public XrayprocessProcessInstance(org.acme.XrayprocessProcess process, XrayprocessModel value, org.kie.api.runtime.process.ProcessRuntime processRuntime) {
        super(process, value, processRuntime);
    }

    public XrayprocessProcessInstance(org.acme.XrayprocessProcess process, XrayprocessModel value, java.lang.String businessKey, org.kie.api.runtime.process.ProcessRuntime processRuntime) {
        super(process, value, businessKey, processRuntime);
    }

    public XrayprocessProcessInstance(org.acme.XrayprocessProcess process, XrayprocessModel value, org.kie.api.runtime.process.ProcessRuntime processRuntime, org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        super(process, value, processRuntime, wpi);
    }

    public XrayprocessProcessInstance(org.acme.XrayprocessProcess process, XrayprocessModel value, org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        super(process, value, wpi);
    }

    protected java.util.Map<String, Object> bind(XrayprocessModel variables) {
        return variables.toMap();
    }

    protected void unbind(XrayprocessModel variables, java.util.Map<String, Object> vmap) {
        variables.fromMap(this.id(), vmap);
    }
}
