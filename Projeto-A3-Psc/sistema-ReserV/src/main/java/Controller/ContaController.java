package Controller;

import Model.Cliente;
import Model.Pessoa;
import Service.GerenciadorContas;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST responsável por expor as operações de contas de usuários
 * (clientes e administradores) como endpoints HTTP.
 */
@RestController
@RequestMapping("/contas")
public class ContaController {

    private final GerenciadorContas gerenciadorContas = new GerenciadorContas();

    /**
     * POST /contas
     * Cadastra um novo cliente. Corpo da requisição em JSON, por exemplo:
     * { "nome": "...", "cpf": "...", "email": "...", "senha": "..." }
     */
    @PostMapping
    public String cadastrar(@RequestBody Cliente cliente) {
        cliente.setTipo("Cliente");
        gerenciadorContas.cadastrarCliente(cliente);
        return "Cliente cadastrado com sucesso!";
    }

    /**
     * GET /contas/{id}
     * Busca um cliente pelo ID.
     */
    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable int id) {
        return gerenciadorContas.obterClientePorId(id);
    }

    /**
     * GET /contas
     * Lista todos os clientes cadastrados.
     */
    @GetMapping
    public List<Cliente> listarTodos() {
        return gerenciadorContas.visualizarClientes();
    }

    /**
     * PUT /contas/{id}
     * Atualiza os dados de um cliente existente.
     */
    @PutMapping("/{id}")
    public String atualizar(@PathVariable int id, @RequestBody Cliente novosDados) {
        gerenciadorContas.alterarClientePorId(id, novosDados);
        return "Dados atualizados com sucesso!";
    }

    /**
     * DELETE /contas/{id}
     * Remove um cliente pelo ID.
     */
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable int id) {
        gerenciadorContas.deletarClientePorId(id);
        return "Cliente deletado com sucesso!";
    }

    /**
     * POST /contas/login
     * Autentica um usuário. Corpo da requisição em JSON, por exemplo:
     * { "email": "...", "senha": "..." }
     */
    @PostMapping("/login")
    public Pessoa login(@RequestBody LoginRequest request) {
        return gerenciadorContas.autenticarPessoa(request.getEmail(), request.getSenha());
    }

    /**
     * Classe auxiliar para representar o corpo da requisição de login,
     * já que login usa apenas email e senha (não um Cliente completo).
     */
    public static class LoginRequest {
        private String email;
        private String senha;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
    }
}