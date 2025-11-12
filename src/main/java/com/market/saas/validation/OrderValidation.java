package com.market.saas.validation;

import com.market.saas.model.OrderEntity;

public class OrderValidation {
    public void validate(OrderEntity order) {
        if (order.getUserId() == null) {
            throw new RuntimeException("O ID do usuário é obrigatório");
        }

        if (order.getStatus() == null || order.getStatus().trim().isEmpty()) {
            throw new RuntimeException("O status do pedido é obrigatório!");
        }

        if (order.getDescription() != null && order.getDescription().length() > 200) {
            throw new RuntimeException("A descrição não pode ter mais do que 200 caracteres!");
        }

        if (order.getCreatedAt() == null) {
            throw new RuntimeException("A data de criação é obrigatória!");
        }
    }
}
