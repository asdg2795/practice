package com.mangezjs.practce.service;

import com.mangezjs.practce.exception.Errorcode;
import com.mangezjs.practce.exception.PracticeException;
import com.mangezjs.practce.model.entity.MemberEntity;
import com.mangezjs.practce.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{

    private final MemberRepository memberRepository;


    public MemberEntity saveMember(MemberEntity memberEntity){
        validateDuplicateMember(memberEntity);
        return memberRepository.save(memberEntity);
    }

    public void validateDuplicateMember(MemberEntity memberEntity){
        MemberEntity findMember = memberRepository.findByEmail(memberEntity.getEmail());
        if(findMember != null){
            throw new PracticeException(Errorcode.DUPLICATED_USER_NAME);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByEmail(username);

        if (memberEntity == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        return User.builder()
                .username(memberEntity.getEmail())
                .password(memberEntity.getPassword())
                .roles(memberEntity.getRole().toString())
                .build();
    }

    public boolean userEmailCheck(String email, String name){
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        return memberEntity != null && memberEntity.getName().equalsIgnoreCase(name);
    }




}
