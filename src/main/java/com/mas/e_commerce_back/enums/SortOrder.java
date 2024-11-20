package com.mas.e_commerce_back.enums;

import lombok.Getter;

@Getter
public enum SortOrder {
    asc("asc"),
    desc("desc");
    private final String value;

    SortOrder(String value) {
        this.value = value;
    }
}
