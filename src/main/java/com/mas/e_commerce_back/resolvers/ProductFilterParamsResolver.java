package com.mas.e_commerce_back.resolvers;

import com.mas.e_commerce_back.dtos.ProductFilterParamsDTO;
import com.mas.e_commerce_back.enums.SortBy;
import com.mas.e_commerce_back.enums.SortOrder;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;
import java.util.Map;

public class ProductFilterParamsResolver implements HandlerMethodArgumentResolver {


    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 24;
    private static final SortOrder DEFAULT_SORT_ORDER = SortOrder.asc;
    private static final SortBy DEFAULT_SORT_BY = SortBy.price;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ProductFilterParamsDTO.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Map<String, String> filters = new HashMap<>();
        Integer page = DEFAULT_PAGE;
        Integer pageSize = DEFAULT_PAGE_SIZE;
        SortOrder sortOrder = DEFAULT_SORT_ORDER;
        SortBy sortBy = DEFAULT_SORT_BY;

        try {
            page = Integer.parseInt(webRequest.getParameter("page"));

        }  catch (Exception e) {
        }


        try {
            pageSize = Integer.parseInt(webRequest.getParameter("pageSize")) ;
        }  catch (Exception e) {
        }

        try {
            sortOrder = SortOrder.valueOf(webRequest.getParameter("sortOrder"));
        }  catch (Exception e) {
        }

        try {
            sortBy = SortBy.valueOf(webRequest.getParameter("sortBy"));
        }  catch (Exception e) {
        }

        webRequest.getParameterMap().forEach((key, value) -> {
            if (!key.equals("page") && !key.equals("pageSize") && !key.equals("sortOrder") && !key.equals("sortBy") && (value[0].length() > 0)) {
                filters.put(key, value[0]);
            }
        });

        System.out.println(filters + ", pageNumber: " + page + ", pageSize: " + pageSize + ", sortOrder: " + sortOrder + ", sortBy: " + sortBy);
        return new ProductFilterParamsDTO(filters, page, pageSize, sortOrder, sortBy);
    }
}
