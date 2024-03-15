package com.estsoft.blogjpa.common.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private int count;
    private T data;

}
