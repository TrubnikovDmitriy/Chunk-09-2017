package application.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class UserSignIn extends UserBase {

    @JsonProperty(value = "login", required = true)
    private String login;

    public UserSignIn() { }

    public UserSignIn(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserSignIn)) return false;

        final UserSignIn that = (UserSignIn) obj;

        return login.equals(that.login);
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}
