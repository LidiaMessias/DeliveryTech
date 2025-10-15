package com.deliverytech.delivery_api.controller;

import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.ClienteDTO;
import com.deliverytech.delivery_api.dto.ClienteResponseDTO;
//import com.deliverytech.delivery_api.model.Cliente;
import com.deliverytech.delivery_api.service.ClienteServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteServiceImpl clienteService;

    // Cadastrar um novo cliente
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        
        ClienteResponseDTO clienteSalvo = clienteService.cadastrarCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);

    }

    // Buscar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        ClienteResponseDTO cliente = clienteService.buscarClientePorId(id);   
        return ResponseEntity.ok(cliente);
        
    }

    // Buscar cliente por email
    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorEmail(@PathVariable String email) {
        ClienteResponseDTO cliente = clienteService.buscarClientePorEmail(email);      
        return ResponseEntity.ok(cliente);
        
    }

    // Listar todos os clientes ativos
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos() {
        List<ClienteResponseDTO> clientes = clienteService.listarClientesAtivos();
        return ResponseEntity.ok(clientes);
    }

    // Atualizar dados do cliente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable Long id,
            @Valid @RequestBody ClienteDTO clienteDTO) {
        
        ClienteResponseDTO clienteAtualizado = clienteService.atualizarCliente(id, clienteDTO);
        return ResponseEntity.ok(clienteAtualizado);
        
    }

    // Ativar e Desativar cliente
    @PatchMapping("/{id}/status")
    public ResponseEntity<ClienteResponseDTO> ativarDesativarCliente(@PathVariable Long id) {
        
        ClienteResponseDTO cliente = clienteService.ativarDesativarCliente(id);
        return ResponseEntity.ok(cliente);
        
    }

    
    /* 
    // Listar todos os clientes
    @GetMapping("/todos")
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        List<ClienteDTO> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    } 

    @GetMapping("/com-pedidos")
    public ResponseEntity<List<ClienteDTO>> listarAtivosComPedidos() {
        List<ClienteDTO> clientes = clienteService.listarClientesAtivosComPedidos();
        return ResponseEntity.ok(clientes);
    }

    // Buscar clientes por nome
    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
        List<Cliente> clientes = clienteService.buscarPorNome(nome);
        return ResponseEntity.ok(clientes);
    }
    */

}
