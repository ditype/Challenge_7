package com.challenge.mycinema.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageModelPagination {
    private String message;
    private boolean status;
    private Object data;
    private Integer currentPage;
    private Integer totalItems;
    private Integer totalPages;
    private Integer numberOfElement;

}