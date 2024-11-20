package com.mas.e_commerce_back.dtos;

import com.mas.e_commerce_back.enums.SortBy;
import com.mas.e_commerce_back.enums.SortOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ProductFilterParamsDTO {

    private  final Map<String, String> filters;
    private final Integer page;
    private final Integer pageSize;
    private final SortOrder sortOrder;
    private final SortBy sortBy;


    public ProductFilterParamsDTO(Map<String, String> filters, Integer page, Integer pageSize, String sortOrder, String sortBy) {
        this.filters = filters;
        this.page = page;
        this.pageSize = pageSize;
        this.sortOrder = SortOrder.valueOf(sortOrder);
        this.sortBy = SortBy.valueOf(sortBy);
    }
}
