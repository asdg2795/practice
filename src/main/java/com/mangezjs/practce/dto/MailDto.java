package com.mangezjs.practce.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MailDto {
    private String address;
    private String title;
    private String message;
}
