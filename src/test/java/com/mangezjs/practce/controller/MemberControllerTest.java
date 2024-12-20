package com.mangezjs.practce.controller;

import com.mangezjs.practce.dto.MemberFormDto;
import com.mangezjs.practce.model.entity.MemberEntity;
import com.mangezjs.practce.service.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    public MemberEntity createMember(String email, String password){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword(password);
        MemberEntity memberEntity = MemberEntity.createMember(memberFormDto , passwordEncoder);
        return memberService.saveMember(memberEntity);
    }

    @Test
    @WithMockUser
    void 로그인_성공_테스트() throws Exception {
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email,password);

        mockMvc.perform(formLogin().loginProcessingUrl("/member/login")
                .userParameter("email").user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    void 로그인_실패_테스트_이메일ver() throws Exception {
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email,password);

        mockMvc.perform(formLogin("/member/login")
                .userParameter("email").user("wrong@email.com").password(password))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

    @Test
    void 로그인_실패_테스트_비밀번호ver() throws Exception {
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email,password);

        mockMvc.perform(formLogin("/member/login")
                .userParameter("email").user(email).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }





}