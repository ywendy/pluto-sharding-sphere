package com.pluto.jdbc01.order.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Order  implements Serializable {
    private Long id;

    private Long userId;

    private String status;


}