package com.jjoony.assetmanagement.domain.auth.service;

import com.jjoony.assetmanagement.global.exception.ApiException;
import com.jjoony.assetmanagement.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final StringRedisTemplate redisTemplate;

    //redis Refresh Token 저장
    public void saveToken(String email, String refreshToken, long expiration) {
        try {
            redisTemplate.opsForValue().set("refresh_token" + email, refreshToken, expiration, TimeUnit.MILLISECONDS);
        } catch (RedisConnectionFailureException e) {
            throw new ApiException(ErrorCode.REDIS_CONNECTION_ERROR);
        } catch (RedisSystemException e) {
            throw new ApiException(ErrorCode.REDIS_COMMAND_ERROR);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.REDIS_DATA_ERROR);
        }
    }

    //redis Refresh Token 조회
    public String getToken(String email) {
        try {
            return redisTemplate.opsForValue().get("refresh_token" + email);
        } catch (RedisConnectionFailureException e) {
            throw new ApiException(ErrorCode.REDIS_CONNECTION_ERROR);
        } catch (RedisSystemException e) {
            throw new ApiException(ErrorCode.REDIS_COMMAND_ERROR);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.REDIS_DATA_ERROR);
        }
    }

    //redis Refresh Token 삭제
    public void deleteToken(String email) {
        try {
            redisTemplate.delete("refresh_token" + email);
        } catch (RedisConnectionFailureException e) {
            throw new ApiException(ErrorCode.REDIS_CONNECTION_ERROR);
        } catch (RedisSystemException e) {
            throw new ApiException(ErrorCode.REDIS_COMMAND_ERROR);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.REDIS_DATA_ERROR);
        }
    }

}
