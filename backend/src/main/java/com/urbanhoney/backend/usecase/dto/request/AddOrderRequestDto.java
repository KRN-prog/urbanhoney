package com.urbanhoney.backend.usecase.dto.request;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderRequestDto {
    private List<Integer> articeList = new ArrayList<>();
    private String total;
    private Integer userId;
}
