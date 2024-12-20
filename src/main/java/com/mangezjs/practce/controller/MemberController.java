package com.mangezjs.practce.controller;

import com.mangezjs.practce.dto.MemberFormDto;
import com.mangezjs.practce.exception.PracticeException;
import com.mangezjs.practce.model.entity.MemberEntity;
import com.mangezjs.practce.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/join")
    public String MemberForm(Model model){
        model.addAttribute("MemberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value = "/join")
    public String MemberJoin(@Valid @ModelAttribute("MemberFormDto") MemberFormDto memberFormDto,
                             BindingResult bindingResult,
                             Model model){
        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }

        try{
            MemberEntity member = MemberEntity.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }catch(PracticeException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String MemberLogin(){
        return "member/loginForm";
    }

    @GetMapping(value = "/login/fail")
    public String memberLoginFail(Model model){
        model.addAttribute("loginFailMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/loginForm";
    }

    @GetMapping(value = "/findPw")
    public String findPw(){
        return "member/findPwForm";
    }





}
