package io.luan.jerry.promotion.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PublishPromotionDTO implements Serializable {

    private Long itemId;

    private Long newPrice;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
