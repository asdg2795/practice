package com.mangezjs.practce.model.entity;

import com.mangezjs.practce.constant.Role;
import com.mangezjs.practce.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;


@Table(name = "member")
@Entity
@Getter
@Setter
@ToString
public class MemberEntity extends BaseEntity{
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;

    @Column(unique = true)
    private String email;

    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static MemberEntity createMember(MemberFormDto memberFormDto,
                                            PasswordEncoder passwordEncoder){
        MemberEntity member = new MemberEntity();
        member.setAddress(memberFormDto.getAddress());
        member.setEmail(memberFormDto.getEmail());
        member.setName(memberFormDto.getName());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.Admin);
        return member;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}
