package com.develop.web.domain.auth.service;

import com.develop.web.domain.auth.dto.LoginRequest;
import com.develop.web.domain.auth.exception.MemberChecker;
import com.develop.web.global.exception.exception.CustomException;
import org.springframework.stereotype.Service;

@Service
public class LoginCheck {

    private final MemberChecker memberChecker;

    public LoginCheck(MemberChecker memberChecker) {
        this.memberChecker = memberChecker;
    }

    /** @description 아이디와 비밀번호 검사 */
    public void getAccount(LoginRequest request) throws CustomException {
        String account = request.getAccount();
        String password = request.getPassword();

        memberChecker.userid(account);
        memberChecker.password(account, password);
    }
}
