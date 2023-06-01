package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item {
    private int itemId;
    private int itemCategory;
    private String postTitle;
    private int postWriterId;
    private String postWrittenDate;
    private String postContent;
    private String postImgUrl;
    private int itemPrice;
    private int itemStatus;
    private int itemLikedCNT;
    private int locationCode;
    private String pullUpDate;
    private Boolean priceOffer; //가격제안 여부
}
