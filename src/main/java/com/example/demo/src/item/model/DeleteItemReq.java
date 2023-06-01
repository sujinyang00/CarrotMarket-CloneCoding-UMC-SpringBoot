package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteItemReq {
    private int itemId;

    private int itemCategory;
    private int userId;
}
