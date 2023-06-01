package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.descriptor.sql.TinyIntTypeDescriptor;

import java.math.BigInteger;
import java.util.BitSet;

@Getter
@Setter
@AllArgsConstructor
public class PostItemReq {
    private int itemId;

    private int itemCategory;
    private String postTitle;
    private String postContent;
    private String postWrittenDate;
    private String postImgUrl;
    private int itemPrice;
    private int itemLikedCNT;
    private int locationCode;
    private Boolean priceOffer; //가격제안 여부
}
