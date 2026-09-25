package com.ryvex.server.service.security;

import com.ryvex.server.model.User;
import com.ryvex.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CurrentUserService {

    private final UserRepository userRepository;

    public CurrentUserService(
            UserRepository userRepository
    ) {

        this.userRepository =
                userRepository;
    }

    public User requireUser(
            String username
    ) {

        return userRepository
                .findFirstByUsernameIgnoreCase(
                        username
                )
                .orElseThrow(
                        () ->
                                new IllegalStateException(
                                        "Authenticated user could not be found."
                                )
                );
    }

    public Long requireUserId(
            String username
    ) {

        return requireUser(
                username
        ).getId();
    }
}