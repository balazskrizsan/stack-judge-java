package com.kbalazsworks.stackjudge.session.services;

import com.kbalazsworks.stackjudge.session.entities.SessionState;
import com.kbalazsworks.stackjudge.session.entities.User;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
public class SessionService
{
    public SessionState getSessionState()
    {
        return new SessionState(
            new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            new User(1L)
        );
    }
}
