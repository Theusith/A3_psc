package Service;

import Model.Reserva;
import Repository.ReservaRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe é responsável por gerenciar operações relacionadas às reservas de viagem.
 * Inclui métodos para adicionar, visualizar, editar e excluir reservas no banco de dados.
 */
public class GerenciadorReservas {

    private ReservaRepository repository = new ReservaRepository();

    /**
     * Adiciona uma nova reserva de viagem ao banco de dados.
     * @param reserva Objeto Reserva contendo as informações da reserva a ser adicionada.
     */
    public void adicionarReserva(Reserva reserva) {
        try {
            repository.inserir(reserva);
            System.out.println("Reserva adicionada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar reserva: " + e.getMessage());
        }
    }

    /**
     * Retorna uma lista de reservas de viagem associadas a um cliente específico.
     * @param idCliente ID do cliente para o qual as reservas devem ser recuperadas.
     * @return Lista de objetos Reserva associados ao cliente especificado.
     */
    public List<Reserva> visualizarReservasPorCliente(int idCliente) {
        try {
            return repository.buscarPorCliente(idCliente);
        } catch (SQLException e) {
            System.err.println("Erro ao visualizar reservas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Atualiza os detalhes de uma reserva de viagem existente no banco de dados.
     * @param idCliente ID do cliente proprietário da reserva.
     * @param idReservas ID da reserva a ser atualizada.
     * @param reservaAtualizada Objeto Reserva com os novos detalhes da reserva.
     */
    public void editarReserva(int idCliente, int idReservas, Reserva reservaAtualizada) {
        try {
            boolean sucesso = repository.atualizar(idCliente, idReservas, reservaAtualizada);
            if (sucesso) {
                System.out.println("Reserva atualizada com sucesso!");
            } else {
                System.out.println("Reserva não encontrada ou não pertence ao cliente.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao editar reserva: " + e.getMessage());
        }
    }

    /**
     * Remove uma reserva de viagem específica do banco de dados com base no ID da reserva.
     * @param idReservas ID da reserva a ser deletada.
     */
    public void deletarReservaPorId(int idReservas) {
        try {
            boolean sucesso = repository.deletarPorId(idReservas);
            if (sucesso) {
                System.out.println("Reserva deletada com sucesso!");
            } else {
                System.out.println("Reserva não encontrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar reserva: " + e.getMessage());
        }
    }

    /**
     * Remove todas as reservas de viagem associadas a um cliente específico do banco de dados.
     * @param idCliente ID do cliente para o qual todas as reservas devem ser deletadas.
     */
    public void deletarReservasPorCliente(int idCliente) {
        try {
            boolean sucesso = repository.deletarPorCliente(idCliente);
            if (sucesso) {
                System.out.println("Reservas do cliente deletadas com sucesso!");
            } else {
                System.out.println("Nenhuma reserva encontrada para o cliente.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar reservas: " + e.getMessage());
        }
    }
}