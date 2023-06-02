package com.challenge.mycinema.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageModel {
    private String message;
    private boolean status;
    private Object data;
    private String code;

}