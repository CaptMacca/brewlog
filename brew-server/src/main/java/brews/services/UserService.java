package brews.services;

import brews.domain.User;

import java.util.Set;

public interface UserService {

    User getCurrentUserDetails();
    User updateUserDetails(String username, User user);
    boolean updatePassword(String oldPassword, String updatePassword);
    User registerUser(User user, Set<String> roles);
}
