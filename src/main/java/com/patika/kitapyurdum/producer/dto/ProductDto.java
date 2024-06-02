package com.patika.kitapyurdum.producer.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {

    private String name;
    private String desc;

}
