package com.academy.mvc.repository;

import com.academy.mvc.entity.PedidoCompra;
import com.academy.mvc.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoCompraRepository extends JpaRepository<PedidoCompra, Long> {
    List<PedidoCompra> findByCliente(Cliente cliente);
    List<PedidoCompra> findByClienteId(Long clienteId);
    Optional<PedidoCompra> findByNumeroPedido(String numeroPedido);
    boolean existsByNumeroPedido(String numeroPedido);
}