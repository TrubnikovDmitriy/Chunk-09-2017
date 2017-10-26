package application.entities;

import application.models.user.UserModel;

import javax.persistence.*;


@Entity
@Table(name = "users")
@SuppressWarnings("unused")
public class UserEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = MAX_USERNAME_LENGTH)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false, length = MAX_EMAIL_LENGTH)
    private String email;

    public UserEntity() { }

    public UserEntity(UserModel userModel) {
        this.username = userModel.getUsername();
        this.password = userModel.getPassword();
        this.email = userModel.getEmail();
    }

    public void update(UserModel userModel) {
        if (userModel.getEmail() != null) {
            this.email = userModel.getEmail();
        }
        if (userModel.getUsername() != null) {
            this.username = userModel.getUsername();
        }
        if (userModel.getPassword() != null) {
            this.password = userModel.getPassword();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private static final int MAX_EMAIL_LENGTH = 50;
    private static final int MAX_USERNAME_LENGTH = 40;
}
