package com.mangezjs.practce.service;

import com.mangezjs.practce.dto.MailDto;
import com.mangezjs.practce.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private static final String FROM_ADDRESS = "your-email@example.com"; // 실제 보내는 이메일 주소
    private final MemberRepository memberRepository;

    public MailDto createMailAndChangePassword(String email, String name){
        String str = getTempPassword();
        MailDto mailDto = new MailDto();
        mailDto.setAddress(email);
        mailDto.setTitle("임시비밀번호 안내 이메일입니다.");
        mailDto.setMessage("안녕하세요 Mangez 입니다.\n"
                +"회원님의 임시 비밀번호는 ["+str+"] 입니다\n"
                +"로그인 후 비밀번호를 변경해주세요.");
        updatePassword(str, email);
        return mailDto;
    }

    public void updatePassword(String str, String email){
        String pw = passwordEncoder.encode(str);
        Long id = memberRepository.findByEmail(email).getId();
        memberRepository.updatePw(pw, id);
    }

    public String getTempPassword(){
        char[] charset = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Y', 'Z'};
        String str = "";

        int idx = 0;
        for(int i = 0; i < 10; i++){
            idx = (int) (charset.length * Math.random());
            str += charset[idx];
        }
        return str;
    }

    @Async
    public void mailSend(MailDto mailDto){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailDto.getAddress()); // 수신자 이메일
        mailMessage.setSubject(mailDto.getTitle()); // 제목
        mailMessage.setText(mailDto.getMessage()); // 메시지 본문
        mailMessage.setFrom(FROM_ADDRESS); // 발신자 이메일
        mailMessage.setReplyTo(FROM_ADDRESS); // 회신 이메일
        javaMailSender.send(mailMessage);
    }
}