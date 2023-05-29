package Jason.Mini.Project.repositories;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import Jason.Mini.Project.model.OtpRedis;

@Repository
public class OtpRepository {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public OtpRedis save(String username, final OtpRedis otpModel) {
        redisTemplate.opsForValue().set(username, otpModel);
        redisTemplate.expire(username, 5, TimeUnit.MINUTES);
        return findOtpByUsername(username);
    }

    public OtpRedis findOtpByUsername(final String username) {
        OtpRedis result = (OtpRedis) redisTemplate.opsForValue()
                .get(username);
        return result;
    }

    public void clearOTP(String username) {
        redisTemplate.delete(username);
    }
}
