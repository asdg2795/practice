package com.mangezjs.practce.controller;

import com.mangezjs.practce.dto.MailDto;
import com.mangezjs.practce.dto.MemberFormDto;
import com.mangezjs.practce.service.MailService;
import com.mangezjs.practce.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping("/member/findPw")
    public @ResponseBody Map<String, Object> findAndSendPw(@RequestParam("email") String email,
                                                           @RequestParam("name") String name) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 이메일과 이름 입력 여부 확인
            if ((email == null || email.trim().isEmpty()) && (name == null || name.trim().isEmpty())) {
                response.put("success", false);
                response.put("message", "이메일과 이름을 모두 입력해주세요.");
                return response;
            }
            if (email == null || email.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "이메일을 입력해주세요.");
                return response;
            }
            if (name == null || name.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "이름을 입력해주세요.");
                return response;
            }

            // 사용자 존재 여부 확인
            boolean isUserExist = memberService.userEmailCheck(email, name);
            if (!isUserExist) {
                response.put("success", false);
                response.put("message", "입력된 정보와 일치하는 사용자를 찾을 수 없습니다.");
                return response;
            }

            // 메일 생성 및 발송
            MailDto mailDto = mailService.createMailAndChangePassword(email, name);
            mailService.mailSend(mailDto);

            response.put("success", true);
            response.put("message", "임시 비밀번호가 이메일로 전송되었습니다.");
        } catch (Exception e) {
            // 예외 처리 및 로깅
            response.put("success", false);
            response.put("message", "처리 중 오류가 발생했습니다. 관리자에게 문의하세요.");
            System.err.println("Error occurred while processing password recovery: " + e.getMessage());
            e.printStackTrace();
        }

        return response; // 자동으로 JSON 형식으로 변환되어 반환됨
    }


}
