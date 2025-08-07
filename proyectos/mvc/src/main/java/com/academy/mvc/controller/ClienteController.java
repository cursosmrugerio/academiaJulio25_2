package com.academy.mvc.controller;

import com.academy.mvc.entity.Cliente;
import com.academy.mvc.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "clientes/lista";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }
    
    @PostMapping
    public String guardarCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Ya existe un cliente con ese email");
            return "redirect:/clientes/nuevo";
        }
        
        clienteRepository.save(cliente);
        redirectAttributes.addFlashAttribute("mensaje", "Cliente guardado exitosamente");
        return "redirect:/clientes";
    }
    
    @GetMapping("/{id}")
    public String verCliente(@PathVariable Long id, Model model) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            model.addAttribute("cliente", cliente.get());
            return "clientes/detalle";
        }
        return "redirect:/clientes";
    }
    
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            model.addAttribute("cliente", cliente.get());
            return "clientes/formulario";
        }
        return "redirect:/clientes";
    }
    
    @PostMapping("/{id}")
    public String actualizarCliente(@PathVariable Long id, @ModelAttribute Cliente cliente, 
                                   RedirectAttributes redirectAttributes) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isPresent()) {
            Cliente clienteActual = clienteExistente.get();
            
            if (!clienteActual.getEmail().equals(cliente.getEmail()) && 
                clienteRepository.existsByEmail(cliente.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "Ya existe un cliente con ese email");
                return "redirect:/clientes/" + id + "/editar";
            }
            
            cliente.setId(id);
            clienteRepository.save(cliente);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente actualizado exitosamente");
        }
        return "redirect:/clientes";
    }
    
    @PostMapping("/{id}/eliminar")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            clienteRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente eliminado exitosamente");
        }
        return "redirect:/clientes";
    }
}