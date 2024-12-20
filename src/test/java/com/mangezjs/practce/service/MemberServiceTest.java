package com.mangezjs.practce.service;

import com.mangezjs.practce.dto.MemberFormDto;
import com.mangezjs.practce.exception.Errorcode;
import com.mangezjs.practce.exception.PracticeException;
import com.mangezjs.practce.model.entity.MemberEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberEntity createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");
        return MemberEntity.createMember(memberFormDto ,passwordEncoder);
    }


    @Test
    void 회원가입이_정상적으로_동작하는_경우(){
       MemberEntity memberEntity = createMember();
       MemberEntity savedMember = memberService.saveMember(memberEntity);

       assertEquals(memberEntity.getEmail(), savedMember.getEmail());
       assertEquals(memberEntity.getName(), savedMember.getName());
       assertEquals(memberEntity.getAddress(), savedMember.getAddress());
       assertEquals(memberEntity.getPassword(), savedMember.getPassword());
       assertEquals(memberEntity.getRole(), savedMember.getRole());
    }

    @Test
    void 중복_회원가입(){
        // given
        MemberEntity member1 = createMember();
        MemberEntity member2 = createMember();
        memberService.saveMember(member1);

        // when
        Throwable e= assertThrows(PracticeException.class, () ->{
            memberService.saveMember(member2);
        });

        // then
        assertEquals(Errorcode.DUPLICATED_USER_NAME.getMessage(), e.getMessage());
    }


}