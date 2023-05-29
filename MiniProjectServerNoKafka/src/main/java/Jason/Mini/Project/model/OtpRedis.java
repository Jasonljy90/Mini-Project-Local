package Jason.Mini.Project.model;

import java.io.Serializable;

public class OtpRedis implements Serializable {
    private String otp;
    private String username;

    public OtpRedis() {
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
