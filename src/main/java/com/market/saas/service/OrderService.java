package com.market.saas.service;

import com.market.saas.model.OrderEntity;
import com.market.saas.repository.OrderRepository;
import com.market.saas.validation.OrderValidation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderEntity createOrder(OrderEntity orderEntity) {
        OrderValidation validator = new OrderValidation();
        validator.validate(orderEntity);

        OrderEntity newOrder = new OrderEntity();
        newOrder.setUserId(orderEntity.getUserId());
        newOrder.setStatus("PENDING");
        newOrder.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(newOrder);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }


    public void deleteOrderById(Long id) {
        Optional<OrderEntity> pedido = orderRepository.findById(id);
        if (pedido.isEmpty()) {
            throw new RuntimeException("Pedido com ID! " + id + " não encontrado");
        }
        if (!"PENDING".equals(pedido.get().getStatus())) {
            throw new RuntimeException("Pedido não pode ser deletado!");
        }

        orderRepository.deleteById(id);
    }

    public OrderEntity updateOrder(OrderEntity orderEntity, Long id) {
        Optional<OrderEntity> pedidoDoBanco = orderRepository.findById(id);

        if (pedidoDoBanco.isEmpty()) {
            throw new RuntimeException("Pedido não encontrado!");
        }

        pedidoDoBanco.get().setUserId(orderEntity.getUserId());
        pedidoDoBanco.get().setStatus(orderEntity.getStatus());
        pedidoDoBanco.get().setDescription(orderEntity.getDescription());

        return orderRepository.save(pedidoDoBanco.get());
    }

    public OrderEntity findOrderById(Long id) {
        Optional<OrderEntity> pedido = orderRepository.findById(id);
        if (pedido.isEmpty()) {
            throw new RuntimeException("Pedido com ID " + id + " não encontrado!");
        }
        return pedido.get();
    }
}
