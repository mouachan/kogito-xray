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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PATCH;
import org.jbpm.util.JsonSchemaUtil;
import org.kie.kogito.Application;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.ProcessInstance;
import org.kie.kogito.process.ProcessInstanceExecutionException;
import org.kie.kogito.process.ProcessInstanceNotFoundException;
import org.kie.kogito.process.ProcessInstanceReadMode;
import org.kie.kogito.process.WorkItem;
import org.kie.kogito.process.workitem.Policies;
import org.kie.kogito.process.impl.Sig;
import org.kie.kogito.services.uow.UnitOfWorkExecutor;
import org.kie.kogito.auth.IdentityProvider;
import org.jbpm.process.instance.impl.humantask.HumanTaskTransition;
import org.acme.XrayprocessModelOutput;

@Path("/xrayprocess")
@javax.enterprise.context.ApplicationScoped()
public class XrayprocessResource {

    @javax.inject.Inject()
    @javax.inject.Named("xrayprocess")
    Process<XrayprocessModel> process;

    @javax.inject.Inject()
    Application application;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<XrayprocessModelOutput> getResources_xrayprocess() {
        return process.instances().values().stream().map(pi -> pi.variables().toOutput()).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public XrayprocessModelOutput getResource_xrayprocess(@PathParam("id") String id) {
        return process.instances().findById(id, ProcessInstanceReadMode.READ_ONLY).map(pi -> pi.variables().toOutput()).orElseThrow(() -> new NotFoundException());
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public XrayprocessModelOutput deleteResource_xrayprocess(@PathParam("id") final String id) {
        return UnitOfWorkExecutor.executeInUnitOfWork(application.unitOfWorkManager(), () -> process.instances().findById(id).map(pi -> {
            pi.abort();
            return pi.checkError().variables().toOutput();
        })).orElseThrow(() -> new NotFoundException());
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public XrayprocessModelOutput updateModel_xrayprocess(@PathParam("id") String id, XrayprocessModel resource) {
        return UnitOfWorkExecutor.executeInUnitOfWork(application.unitOfWorkManager(), () -> process.instances().findById(id).map(pi -> pi.updateVariables(resource).toOutput())).orElseThrow(() -> new NotFoundException());
    }

    @GET
    @Path("/{id}/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WorkItem> getTasks_xrayprocess(@PathParam("id") String id, @QueryParam("user") final String user, @QueryParam("group") final List<String> groups) {
        return process.instances().findById(id, ProcessInstanceReadMode.READ_ONLY).map(pi -> pi.workItems(Policies.of(user, groups))).orElseThrow(() -> new NotFoundException());
    }

    private static <T, U extends org.kie.kogito.MapOutput> ProcessInstance<T> doTransitionWorkItem(ProcessInstance<T> pi, String workItemId, String phase, U model, String user, List<String> groups) {
        pi.transitionWorkItem(workItemId, HumanTaskTransition.withModel(phase, model, Policies.of(user, groups)));
        return pi;
    }

    @POST
    @Path("/{id}/Task/{workItemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public XrayprocessModelOutput completeTask_Task_0(@PathParam("id") final String id, @PathParam("workItemId") final String workItemId, @QueryParam("phase") @DefaultValue("complete") final String phase, @QueryParam("user") final String user, @QueryParam("group") final List<String> groups, final Xrayprocess_4_TaskOutput model) {
        return UnitOfWorkExecutor.executeInUnitOfWork(application.unitOfWorkManager(), () -> process.instances().findById(id).map(pi -> {
            pi.transitionWorkItem(workItemId, HumanTaskTransition.withModel(phase, model, Policies.of(user, groups)));
            return pi.variables().toOutput();
        })).orElseThrow(() -> new NotFoundException());
    }

    @POST
    @Path("/{id}/Task/{workItemId}/phases/{phase}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public XrayprocessModelOutput taskTransition_Task_0(@PathParam("id") final String id, @PathParam("workItemId") final String workItemId, @PathParam("phase") final String phase, @QueryParam("user") final String user, @QueryParam("group") final List<String> groups, final Xrayprocess_4_TaskOutput model) {
        return UnitOfWorkExecutor.executeInUnitOfWork(application.unitOfWorkManager(), () -> process.instances().findById(id).map(pi -> doTransitionWorkItem(pi, workItemId, phase, model, user, groups)).map(pi -> pi.variables().toOutput()).orElseThrow(() -> new NotFoundException()));
    }

    @PATCH
    @Path("/{id}/Task/{workItemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Xrayprocess_4_TaskOutput updateTask_Task_0(@PathParam("id") final String id, @PathParam("workItemId") final String workItemId, @QueryParam("user") final String user, @QueryParam("group") final List<String> groups, final Map<String, Object> params) {
        return UnitOfWorkExecutor.executeInUnitOfWork(application.unitOfWorkManager(), () -> process.instances().findById(id).map(pi -> Xrayprocess_4_TaskOutput.fromMap(pi.updateWorkItem(workItemId, params, Policies.of(user, groups))))).orElseThrow(() -> new NotFoundException());
    }

    @GET
    @Path("/{id}/Task/{workItemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Xrayprocess_4_TaskInput getTask_Task_0(@PathParam("id") String id, @PathParam("workItemId") String workItemId, @QueryParam("user") final String user, @QueryParam("group") final List<String> groups) {
        return process.instances().findById(id, ProcessInstanceReadMode.READ_ONLY).map(pi -> Xrayprocess_4_TaskInput.from(pi.workItem(workItemId, Policies.of(user, groups)))).orElseThrow(() -> new NotFoundException());
    }

    @GET
    @Path("Task/schema")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getSchema_Task_0() {
        return JsonSchemaUtil.load(this.getClass().getClassLoader(), process.id(), "Task");
    }

    @GET
    @Path("/{id}/Task/{workItemId}/schema")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getSchemaAndPhases_Task_0(@PathParam("id") final String id, @PathParam("workItemId") final String workItemId, @QueryParam("user") final String user, @QueryParam("group") final List<String> groups) {
        return JsonSchemaUtil.addPhases(process, application, id, workItemId, Policies.of(user, groups), JsonSchemaUtil.load(this.getClass().getClassLoader(), process.id(), "Task"));
    }

    @DELETE
    @Path("/{id}/Task/{workItemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public XrayprocessModelOutput abortTask_Task_0(@PathParam("id") final String id, @PathParam("workItemId") final String workItemId, @QueryParam("phase") @DefaultValue("abort") final String phase, @QueryParam("user") final String user, @QueryParam("group") final List<String> groups) {
        return UnitOfWorkExecutor.executeInUnitOfWork(application.unitOfWorkManager(), () -> process.instances().findById(id).map(pi -> {
            pi.transitionWorkItem(workItemId, HumanTaskTransition.withoutModel(phase, Policies.of(user, groups)));
            return pi.variables().toOutput();
        })).orElseThrow(() -> new NotFoundException());
    }

    @POST
    @Path("/{id}/Radiologist/{workItemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public XrayprocessModelOutput completeTask_Radiologist_1(@PathParam("id") final String id, @PathParam("workItemId") final String workItemId, @QueryParam("phase") @DefaultValue("complete") final String phase, @QueryParam("user") final String user, @QueryParam("group") final List<String> groups, final Xrayprocess_6_TaskOutput model) {
        return UnitOfWorkExecutor.executeInUnitOfWork(application.unitOfWorkManager(), () -> process.instances().findById(id).map(pi -> {
            pi.transitionWorkItem(workItemId, HumanTaskTransition.withModel(phase, model, Policies.of(user, groups)));
            return pi.variables().toOutput();
        })).orElseThrow(() -> new NotFoundException());
    }

    @POST
    @Path("/{id}/Radiologist/{workItemId}/phases/{phase}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public XrayprocessModelOutput taskTransition_Radiologist_1(@PathParam("id") final String id, @PathParam("workItemId") final String workItemId, @PathParam("phase") final String phase, @QueryParam("user") final String user, @QueryParam("group") final List<String> groups, final Xrayprocess_6_TaskOutput model) {
        return UnitOfWorkExecutor.executeInUnitOfWork(application.unitOfWorkManager(), () -> process.instances().findById(id).map(pi -> doTransitionWorkItem(pi, workItemId, phase, model, user, groups)).map(pi -> pi.variables().toOutput()).orElseThrow(() -> new NotFoundException()));
    }

    @PATCH
    @Path("/{id}/Radiologist/{workItemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Xrayprocess_6_TaskOutput updateTask_Radiologist_1(@PathParam("id") final String id, @PathParam("workItemId") final String workItemId, @QueryParam("user") final String user, @QueryParam("group") final List<String> groups, final Map<String, Object> params) {
        return UnitOfWorkExecutor.executeInUnitOfWork(application.unitOfWorkManager(), () -> process.instances().findById(id).map(pi -> Xrayprocess_6_TaskOutput.fromMap(pi.updateWorkItem(workItemId, params, Policies.of(user, groups))))).orElseThrow(() -> new NotFoundException());
    }

    @GET
    @Path("/{id}/Radiologist/{workItemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Xrayprocess_6_TaskInput getTask_Radiologist_1(@PathParam("id") String id, @PathParam("workItemId") String workItemId, @QueryParam("user") final String user, @QueryParam("group") final List<String> groups) {
        return process.instances().findById(id, ProcessInstanceReadMode.READ_ONLY).map(pi -> Xrayprocess_6_TaskInput.from(pi.workItem(workItemId, Policies.of(user, groups)))).orElseThrow(() -> new NotFoundException());
    }

    @GET
    @Path("Radiologist/schema")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getSchema_Radiologist_1() {
        return JsonSchemaUtil.load(this.getClass().getClassLoader(), process.id(), "Radiologist");
    }

    @GET
    @Path("/{id}/Radiologist/{workItemId}/schema")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getSchemaAndPhases_Radiologist_1(@PathParam("id") final String id, @PathParam("workItemId") final String workItemId, @QueryParam("user") final String user, @QueryParam("group") final List<String> groups) {
        return JsonSchemaUtil.addPhases(process, application, id, workItemId, Policies.of(user, groups), JsonSchemaUtil.load(this.getClass().getClassLoader(), process.id(), "Radiologist"));
    }

    @DELETE
    @Path("/{id}/Radiologist/{workItemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public XrayprocessModelOutput abortTask_Radiologist_1(@PathParam("id") final String id, @PathParam("workItemId") final String workItemId, @QueryParam("phase") @DefaultValue("abort") final String phase, @QueryParam("user") final String user, @QueryParam("group") final List<String> groups) {
        return UnitOfWorkExecutor.executeInUnitOfWork(application.unitOfWorkManager(), () -> process.instances().findById(id).map(pi -> {
            pi.transitionWorkItem(workItemId, HumanTaskTransition.withoutModel(phase, Policies.of(user, groups)));
            return pi.variables().toOutput();
        })).orElseThrow(() -> new NotFoundException());
    }
}
