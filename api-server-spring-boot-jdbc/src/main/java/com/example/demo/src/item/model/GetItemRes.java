package com.example.demo.src.item.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.descriptor.sql.TinyIntTypeDescriptor;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class GetItemRes {
    private int itemIdx;
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
