package com.application.app.events;

import com.application.app.models.entities.user.User;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private User user;

    public OnRegistrationCompleteEvent(User user) {
        super(user);

        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public OnRegistrationCompleteEvent setUser(User user) {
        this.user = user;
        return this;
    }



}
