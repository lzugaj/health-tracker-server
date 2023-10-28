package com.luv2code.health.tracker.data;

import com.luv2code.health.tracker.domain.User;

public class UserTestData {

    public static User createUser(String firstName, String lastName, String email) {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();
    }
}
