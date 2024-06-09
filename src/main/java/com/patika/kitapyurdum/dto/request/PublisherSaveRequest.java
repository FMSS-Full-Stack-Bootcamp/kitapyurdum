package com.patika.kitapyurdum.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PublisherSaveRequest {

    private String name;
    private LocalDate createdDate;
}
