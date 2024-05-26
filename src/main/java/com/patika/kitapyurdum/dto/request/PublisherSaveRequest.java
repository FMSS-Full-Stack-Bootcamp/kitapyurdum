package com.patika.kitapyurdum.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PublisherSaveRequest {

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;
}
