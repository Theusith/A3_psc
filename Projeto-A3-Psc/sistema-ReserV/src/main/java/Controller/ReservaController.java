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
    public ResponseEntity<String> criar(@RequestBody Reserva reserva) {
        if (reserva.getOrigem() == null || reserva.getOrigem().isBlank()
                || reserva.getDestino() == null || reserva.getDestino().isBlank()
                || reserva.getDataViagem() == null || reserva.getDataViagem().isBlank()) {
            return ResponseEntity.badRequest().body("Origem, destino e data da viagem são obrigatórios.");
        }
        if (gerenciadorContas.obterClientePorId(reserva.getIdCliente()) == null) {
            return ResponseEntity.badRequest().body("Cliente informado não existe.");
        }
        gerenciadorReservas.adicionarReserva(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body("Reserva adicionada com sucesso!");
    }

    /**
     * GET /reservas/cliente/{idCliente}
     * Lista todas as reservas de um cliente específico.
     */
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Reserva>> listarPorCliente(@PathVariable int idCliente) {
        if (gerenciadorContas.obterClientePorId(idCliente) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gerenciadorReservas.visualizarReservasPorCliente(idCliente));
    }

    /**
     * PUT /reservas/{idReserva}/cliente/{idCliente}
     * Atualiza uma reserva existente.
     */
    @PutMapping("/{idReserva}/cliente/{idCliente}")
    public ResponseEntity<String> atualizar(@PathVariable int idReserva, @PathVariable int idCliente, @RequestBody Reserva reservaAtualizada) {
        gerenciadorReservas.editarReserva(idCliente, idReserva, reservaAtualizada);
        return ResponseEntity.ok("Reserva atualizada com sucesso!");
    }

    /**
     * DELETE /reservas/{idReserva}
     * Remove uma reserva específica pelo ID.
     */
    @DeleteMapping("/{idReserva}")
    public ResponseEntity<String> deletar(@PathVariable int idReserva) {
        gerenciadorReservas.deletarReservaPorId(idReserva);
        return ResponseEntity.ok("Reserva deletada com sucesso!");
    }

    /**
     * DELETE /reservas/cliente/{idCliente}
     * Remove todas as reservas de um cliente específico.
     */
    @DeleteMapping("/cliente/{idCliente}")
    public ResponseEntity<String> deletarPorCliente(@PathVariable int idCliente) {
        gerenciadorReservas.deletarReservasPorCliente(idCliente);
        return ResponseEntity.ok("Reservas do cliente deletadas com sucesso!");
    }
}