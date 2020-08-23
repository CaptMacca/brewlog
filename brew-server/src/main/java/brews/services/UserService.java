package brews.services;

import brews.domain.User;

public interface UserService {

    User getCurrentUserDetails();
    User updateUserDetails(String username, User user);
    boolean updatePassword(String oldPassword, String updatePassword);
}
