package com.kbalazsworks.stackjudge.api.controllers.group_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.requests.group_request.GroupCreateRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.services.RequestMapperService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.services.GroupService;
import com.kbalazsworks.stackjudge.session.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("StackPostAction")
@RequestMapping(GroupConfig.CONTROLLER_URI)
public class PostAction
{
    private GroupService   groupService;
    private SessionService sessionService;

    @Autowired
    public void setStackService(GroupService groupService)
    {
        this.groupService = groupService;
    }

    @Autowired
    public void setSessionService(SessionService sessionService)
    {
        this.sessionService = sessionService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<String>> action(GroupCreateRequest request) throws Exception
    {
        new JavaxValidatorService<GroupCreateRequest>().validate(request);

        groupService.create(RequestMapperService.mapToRecord(request, sessionService.getSessionState()));

        ResponseEntityBuilder<String> responseEntityBuilder = new ResponseEntityBuilder<>();

        responseEntityBuilder.setData(null);

        return responseEntityBuilder.build();
    }
}