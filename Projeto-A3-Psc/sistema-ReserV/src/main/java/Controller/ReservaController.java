package Controller;

import Model.Reserva;
import Service.GerenciadorContas;
import Service.GerenciadorReservas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST responsável por expor as operações de reservas de viagem
 * como endpoints HTTP.
 */
@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final GerenciadorReservas gerenciadorReservas = new GerenciadorReservas();
    private final GerenciadorContas gerenciadorContas = new GerenciadorContas();

    /**
     * POST /reservas
     * Cria uma nova reserva. Corpo da requisição em JSON, por exemplo:
     * { "idCliente": 1, "origem": "Belo Horizonte", "destino": "Rio de Janeiro", "dataViagem": "25/12/2026" }
     */
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Reserva reserva) {
        if (reserva.getOrigem() == null || reserva.getOrigem().isBlank()
                || reserva.getDestino() == null || reserva.getDestino().isBlank()
                || reserva.getDataViagem() == null || reserva.getDataViagem().isBlank()) {
            return ResponseEntity.badRequest().body(new ErroResposta("Origem, destino e data da viagem são obrigatórios."));
        }
        if (gerenciadorContas.obterClientePorId(reserva.getIdCliente()) == null) {
            return ResponseEntity.badRequest().body(new ErroResposta("Cliente informado não existe."));
        }
        gerenciadorReservas.adicionarReserva(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

    /**
     * GET /reservas/cliente/{idCliente}
     * Lista todas as reservas de um cliente específico.
     */
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<?> listarPorCliente(@PathVariable int idCliente) {
        if (gerenciadorContas.obterClientePorId(idCliente) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroResposta("Cliente não encontrado."));
        }
        return ResponseEntity.ok(gerenciadorReservas.visualizarReservasPorCliente(idCliente));
    }

    /**
     * PUT /reservas/{idReserva}/cliente/{idCliente}
     * Atualiza uma reserva existente.
     */
    @PutMapping("/{idReserva}/cliente/{idCliente}")
    public ResponseEntity<?> atualizar(@PathVariable int idReserva, @PathVariable int idCliente, @RequestBody Reserva reservaAtualizada) {
        boolean sucesso = gerenciadorReservas.editarReserva(idCliente, idReserva, reservaAtualizada);
        if (!sucesso) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroResposta("Reserva não encontrada ou não pertence ao cliente."));
        }
        return ResponseEntity.ok(reservaAtualizada);
    }

    /**
     * DELETE /reservas/{idReserva}
     * Remove uma reserva específica pelo ID.
     */
    @DeleteMapping("/{idReserva}")
    public ResponseEntity<?> deletar(@PathVariable int idReserva) {
        boolean sucesso = gerenciadorReservas.deletarReservaPorId(idReserva);
        if (!sucesso) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroResposta("Reserva não encontrada."));
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /reservas/cliente/{idCliente}
     * Remove todas as reservas de um cliente específico.
     */
    @DeleteMapping("/cliente/{idCliente}")
    public ResponseEntity<?> deletarPorCliente(@PathVariable int idCliente) {
        boolean sucesso = gerenciadorReservas.deletarReservasPorCliente(idCliente);
        if (!sucesso) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroResposta("Nenhuma reserva encontrada para o cliente."));
        }
        return ResponseEntity.noContent().build();
    }
}