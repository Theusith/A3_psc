package Controller;

import Model.Cliente;
import Model.Pessoa;
import Service.GerenciadorContas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> cadastrar(@RequestBody Cliente cliente) {
        if (cliente.getEmail() == null || cliente.getEmail().isBlank()
                || cliente.getSenha() == null || cliente.getSenha().isBlank()) {
            return ResponseEntity.badRequest().body("Email e senha são obrigatórios.");
        }
        cliente.setTipo("Cliente");
        gerenciadorContas.cadastrarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso!");
    }

    /**
     * GET /contas/{id}
     * Busca um cliente pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable int id) {
        Cliente cliente = gerenciadorContas.obterClientePorId(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    /**
     * GET /contas
     * Lista todos os clientes cadastrados.
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(gerenciadorContas.visualizarClientes());
    }

    /**
     * PUT /contas/{id}
     * Atualiza os dados de um cliente existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable int id, @RequestBody Cliente novosDados) {
        Cliente existente = gerenciadorContas.obterClientePorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        gerenciadorContas.alterarClientePorId(id, novosDados);
        return ResponseEntity.ok("Dados atualizados com sucesso!");
    }

    /**
     * DELETE /contas/{id}
     * Remove um cliente pelo ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id) {
        Cliente existente = gerenciadorContas.obterClientePorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        gerenciadorContas.deletarClientePorId(id);
        return ResponseEntity.ok("Cliente deletado com sucesso!");
    }

    /**
     * POST /contas/login
     * Autentica um usuário. Corpo da requisição em JSON, por exemplo:
     * { "email": "...", "senha": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<Pessoa> login(@RequestBody LoginRequest request) {
        Pessoa pessoa = gerenciadorContas.autenticarPessoa(request.getEmail(), request.getSenha());
        if (pessoa == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(pessoa);
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