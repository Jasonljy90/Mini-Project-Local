package Jason.Mini.Project.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserAccount {
    private Integer userId;
    private boolean isEnabled;

    @NotNull(message = "You must provide your email address")
    @Email(message = "Please enter a valid email format")
    private String email;

    @Size(min = 3, max = 10, message = "Please enter a min of 3 characters and maxinum of 10 characters")
    private String username;

    private String password;
    private String last_login;
    private Integer numFailedLoginAttempt;
    private String token;

    public UserAccount() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public int getNumFailedLoginAttempt() {
        return numFailedLoginAttempt;
    }

    public void setNumFailedLoginAttempt(int numFailedLoginAttempt) {
        this.numFailedLoginAttempt = numFailedLoginAttempt;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

}
