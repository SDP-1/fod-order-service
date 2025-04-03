package com.fod.order_service.entity;


import com.fod.order_service.entity.Enum.OrderStatus;
import com.fod.order_service.entity.Enum.PaymentMethod;
import com.fod.order_service.entity.Enum.PaymentStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "orders")
public class Order extends GenaralModel{



}
