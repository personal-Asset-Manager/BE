package com.jjoony.assetmanagement.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 access token 입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 refresh token 입니다"),
    TOKEN_ALREADY_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 이미 만료되었습니다."),

    REDIS_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Redis 서버와의 연결에 실패했습니다."),
    REDIS_COMMAND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Redis 명령어 처리 중 오류가 발생했습니다."),
    REDIS_DATA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Redis에서 처리된 데이터에 문제가 있습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    MEMBER_ALREADY_SIGNEDUP(HttpStatus.FOUND, "이미 회원가입한 이메일 입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
    private final HttpStatus httpStatus;
    private final String msg;
}
