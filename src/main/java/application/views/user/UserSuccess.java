package application.views.user;

import application.models.user.UserSignUp;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public final class UserSuccess {

    @JsonProperty
    private String username;

    @JsonProperty
    private String email;

    public UserSuccess(UserSignUp user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}