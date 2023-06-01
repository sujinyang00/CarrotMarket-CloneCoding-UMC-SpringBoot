package com.example.demo.src.item.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.descriptor.sql.TinyIntTypeDescriptor;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class GetLikedItemRes {
    private int itemIdx;
    private int itemId;
}
