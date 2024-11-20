package com.mas.e_commerce_back.enums;

import lombok.Getter;

@Getter
public enum SortBy {
    price("price"),
    name("name");

    private final String value;

    SortBy(String value) {
        this.value = value;
    }

}
