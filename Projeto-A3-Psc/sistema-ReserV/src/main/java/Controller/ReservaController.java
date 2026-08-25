package Controller;

import Model.Reserva;
import Service.GerenciadorReservas;
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

    /**
     * POST /reservas
     * Cria uma nova reserva. Corpo da requisição em JSON, por exemplo:
     * { "idCliente": 1, "origem": "Belo Horizonte", "destino": "Rio de Janeiro", "dataViagem": "25/12/2026" }
     */
    @PostMapping
    public String criar(@RequestBody Reserva reserva) {
        gerenciadorReservas.adicionarReserva(reserva);
        return "Reserva adicionada com sucesso!";
    }

    /**
     * GET /reservas/cliente/{idCliente}
     * Lista todas as reservas de um cliente específico.
     */
    @GetMapping("/cliente/{idCliente}")
    public List<Reserva> listarPorCliente(@PathVariable int idCliente) {
        return gerenciadorReservas.visualizarReservasPorCliente(idCliente);
    }

    /**
     * PUT /reservas/{idReserva}/cliente/{idCliente}
     * Atualiza uma reserva existente.
     */
    @PutMapping("/{idReserva}/cliente/{idCliente}")
    public String atualizar(@PathVariable int idReserva, @PathVariable int idCliente, @RequestBody Reserva reservaAtualizada) {
        gerenciadorReservas.editarReserva(idCliente, idReserva, reservaAtualizada);
        return "Reserva atualizada com sucesso!";
    }

    /**
     * DELETE /reservas/{idReserva}
     * Remove uma reserva específica pelo ID.
     */
    @DeleteMapping("/{idReserva}")
    public String deletar(@PathVariable int idReserva) {
        gerenciadorReservas.deletarReservaPorId(idReserva);
        return "Reserva deletada com sucesso!";
    }

    /**
     * DELETE /reservas/cliente/{idCliente}
     * Remove todas as reservas de um cliente específico.
     */
    @DeleteMapping("/cliente/{idCliente}")
    public String deletarPorCliente(@PathVariable int idCliente) {
        gerenciadorReservas.deletarReservasPorCliente(idCliente);
        return "Reservas do cliente deletadas com sucesso!";
    }
}