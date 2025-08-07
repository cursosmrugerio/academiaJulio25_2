package com.academy.mvc.controller;

import com.academy.mvc.entity.Cliente;
import com.academy.mvc.entity.PedidoCompra;
import com.academy.mvc.repository.ClienteRepository;
import com.academy.mvc.repository.PedidoCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/pedidos")
public class PedidoCompraController {
    
    @Autowired
    private PedidoCompraRepository pedidoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @GetMapping
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "pedidos/lista";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("pedido", new PedidoCompra());
        model.addAttribute("clientes", clienteRepository.findAll());
        return "pedidos/formulario";
    }
    
    @PostMapping
    public String guardarPedido(@ModelAttribute PedidoCompra pedido, RedirectAttributes redirectAttributes) {
        if (pedidoRepository.existsByNumeroPedido(pedido.getNumeroPedido())) {
            redirectAttributes.addFlashAttribute("error", "Ya existe un pedido con ese número");
            return "redirect:/pedidos/nuevo";
        }
        
        pedidoRepository.save(pedido);
        redirectAttributes.addFlashAttribute("mensaje", "Pedido guardado exitosamente");
        return "redirect:/pedidos";
    }
    
    @GetMapping("/{id}")
    public String verPedido(@PathVariable Long id, Model model) {
        Optional<PedidoCompra> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            model.addAttribute("pedido", pedido.get());
            return "pedidos/detalle";
        }
        return "redirect:/pedidos";
    }
    
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<PedidoCompra> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            model.addAttribute("pedido", pedido.get());
            model.addAttribute("clientes", clienteRepository.findAll());
            model.addAttribute("estados", PedidoCompra.EstadoPedido.values());
            return "pedidos/formulario";
        }
        return "redirect:/pedidos";
    }
    
    @PostMapping("/{id}")
    public String actualizarPedido(@PathVariable Long id, @ModelAttribute PedidoCompra pedido, 
                                  RedirectAttributes redirectAttributes) {
        Optional<PedidoCompra> pedidoExistente = pedidoRepository.findById(id);
        if (pedidoExistente.isPresent()) {
            PedidoCompra pedidoActual = pedidoExistente.get();
            
            if (!pedidoActual.getNumeroPedido().equals(pedido.getNumeroPedido()) && 
                pedidoRepository.existsByNumeroPedido(pedido.getNumeroPedido())) {
                redirectAttributes.addFlashAttribute("error", "Ya existe un pedido con ese número");
                return "redirect:/pedidos/" + id + "/editar";
            }
            
            pedido.setId(id);
            pedido.setFechaPedido(pedidoActual.getFechaPedido());
            pedidoRepository.save(pedido);
            redirectAttributes.addFlashAttribute("mensaje", "Pedido actualizado exitosamente");
        }
        return "redirect:/pedidos";
    }
    
    @PostMapping("/{id}/eliminar")
    public String eliminarPedido(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<PedidoCompra> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            pedidoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Pedido eliminado exitosamente");
        }
        return "redirect:/pedidos";
    }
    
    @GetMapping("/cliente/{clienteId}")
    public String listarPedidosPorCliente(@PathVariable Long clienteId, Model model) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        if (cliente.isPresent()) {
            var pedidos = pedidoRepository.findByClienteId(clienteId);
            BigDecimal totalFacturado = BigDecimal.ZERO;
            for (PedidoCompra pedido : pedidos) {
                if (pedido.getTotal() != null) {
                    totalFacturado = totalFacturado.add(pedido.getTotal());
                }
            }
                
            model.addAttribute("cliente", cliente.get());
            model.addAttribute("pedidos", pedidos);
            model.addAttribute("totalFacturado", totalFacturado);
            return "pedidos/por-cliente";
        }
        return "redirect:/clientes";
    }
}