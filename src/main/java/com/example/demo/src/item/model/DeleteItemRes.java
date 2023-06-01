package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteItemRes {
    private int itemId;
    private String jwt;
}
