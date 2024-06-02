package com.patika.kitapyurdum.producer.dto;

import com.patika.kitapyurdum.producer.dto.enums.NotificationType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationDto {

    private NotificationType notificationType;
    private List<ProductDto> productDtoList;
    private BigDecimal totalAmount;

}
