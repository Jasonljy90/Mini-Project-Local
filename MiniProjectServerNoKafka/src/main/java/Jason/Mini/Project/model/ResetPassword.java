package Jason.Mini.Project.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class ResetPassword {
    @NotNull(message = "You must provide your email address")
    @Email(message = "Please enter a valid email format")
    private String email;

    private String password;

    public ResetPassword() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
