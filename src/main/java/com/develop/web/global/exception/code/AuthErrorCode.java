package com.develop.web.global.exception.code;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    NO_ACCESS("A0000", "권한이 없습니다."),
    ACCOUNT_NOT_FOUND("A0001", "등록된 계정이 없거나 비정상적인 접근입니다."),
    AUTH_ACCESS_NOT_FOUND("A0002", "승인된 계정이 아닙니다.")
    ;

    private final String code;
    private final String message;

    @Override
    public String code() {
        return this.code;
    }
}
