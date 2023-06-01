package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PutItemPostReq {
    private int itemId;

    private int itemCategory;
    private String updatedDate;
    private String postTitle;
    private String postContent;
    private String postImgUrl;
    private int itemPrice;
    private int itemStatus;
    private int locationCode;
    private Boolean priceOffer; //가격제안 여부
}
