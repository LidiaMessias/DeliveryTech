package com.deliverytech.delivery_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.model.Cliente;
import com.deliverytech.delivery_api.repository.ClienteRepository;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Cadastrar novo cliente
    public Cliente cadastrar(Cliente cliente) {
        // Validar email único
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + cliente.getEmail());
        }
        // Validações de negócio
        validarDadosCliente(cliente);
        // Definir como ativo por padrão
        cliente.setAtivo(true);
        return clienteRepository.save(cliente);
    }

    // Buscar cliente por ID
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    // Buscar cliente por email
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    // Listar todos os clientes ativos
    @Transactional(readOnly = true)
    public List<Cliente> listarAtivos() {
        return clienteRepository.findByAtivoTrue();
    }

    // Atualizar dados do cliente
    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        Cliente clienteExistente = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));

        // Verificar se email não está sendo usado por outro cliente
        if (!clienteExistente.getEmail().equals(clienteAtualizado.getEmail()) &&
                clienteRepository.existsByEmail(clienteAtualizado.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " +
                    clienteAtualizado.getEmail());
        }

        // Atualizar campos (exemplo: nome, telefone, endereço)
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setEndereco(clienteAtualizado.getEndereco());
        // Outras atualizações conforme necessário
        return clienteRepository.save(clienteExistente);
    }

    // Desativar cliente (soft delete)
    public void desativar(Long id) {
        Cliente clienteExistente = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
        clienteExistente.setAtivo(false);
        clienteRepository.save(clienteExistente);
    }

    // Buscar clientes por nome
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Validações de negócio
    private void validarDadosCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }
        if (cliente.getNome().length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres");
        }
    }
}
