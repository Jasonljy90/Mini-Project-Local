package Jason.Mini.Project.services;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import Jason.Mini.Project.model.OtpRedis;
import Jason.Mini.Project.repositories.OtpRepository;

@Qualifier("otpRedisService")
@Service
public class OtpRedisService {

    @Autowired
    OtpRepository otpRepository;

    public String generateSaveOTP(String username, OtpRedis otp) {
        Random random = new Random();
        Integer otpTemp = 100000 + random.nextInt(900000);
        String otpTempStr = Integer.toString(otpTemp);
        otp.setOtp(otpTempStr);
        otpRepository.save(username, otp);
        return otpTempStr;
    }

    public String findOtpByUsername(String username) {
        OtpRedis otpModel = otpRepository.findOtpByUsername(username);

        if (otpModel == null) {
            return "";
        }

        return otpModel.getOtp();
    }

    public void clearOtpByUsername(String username) {
        otpRepository.clearOTP(username);
    }
}
