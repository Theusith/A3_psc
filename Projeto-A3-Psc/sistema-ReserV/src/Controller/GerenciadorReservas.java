package Controller;

import Conexao.Conexao;
import Model.Reserva;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class GerenciadorReservas {

    public void adicionarReserva(Reserva reserva) {
        String sql = "INSERT INTO RESERVAS (idCliente, origem, destino, dataViagem) VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, reserva.getIdCliente());
            stmt.setString(2, reserva.getOrigem());
            stmt.setString(3, reserva.getDestino());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Ajuste o padrão conforme necessário
            LocalDate localDate = LocalDate.parse(reserva.getDataViagem(), formatter);
            Date sqlDate = Date.valueOf(localDate);

            stmt.setDate(4, sqlDate);// Certifique-se de que o tipo de dado da coluna dataViagem no banco seja compatível

            stmt.executeUpdate();
            System.out.println("Reserva adicionada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar reserva: " + e.getMessage());
            // Tratar o erro de forma mais adequada (rollback, log, etc.)
        }
    }

    public List<Reserva> visualizarReservasPorCliente(int idCliente) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE idCliente = ?";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); // Formato desejado para a data

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {


            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                idCliente = (rs.getInt("idCliente"));
                String origem = (rs.getString("origem"));
                String destino = (rs.getString("destino"));
                Date dataViagemSql = rs.getDate("dataViagem");
                int idReserva = rs.getInt("idReservas");
                String dataViagem = formato.format(dataViagemSql); // Formata a data
                Reserva reserva = new Reserva(idCliente, origem, destino, dataViagem);
                reserva.setIdReserva(idReserva);

                reservas.add(reserva);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao visualizar reservas: " + e.getMessage());
            // Tratar o erro de forma mais adequada
        }
        return reservas;
    }

    public void editarReserva(int idCliente, int idReservas, Reserva reservaAtualizada) {
        String sql = "UPDATE reservas SET origem = ?, destino = ?, dataViagem = ? WHERE idReservas = ? AND idCLiente = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, reservaAtualizada.getOrigem());
            stmt.setString(2, reservaAtualizada.getDestino());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Ajuste o padrão conforme necessário
            LocalDate localDate = LocalDate.parse(reservaAtualizada.getDataViagem(), formatter);
            Date sqlDate = Date.valueOf(localDate);

            stmt.setDate(3, sqlDate);// Certifique-se de que o tipo de dado da coluna dataViagem no banco seja compatível
            stmt.setInt(4, idReservas);
            stmt.setInt(5, idCliente); // Adiciona a condição para o idCliente

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Reserva atualizada com sucesso!");
            } else {
                System.out.println("Reserva não encontrada ou não pertence ao cliente.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao editar reserva: " + e.getMessage());
            // Tratar o erro de forma mais adequada (rollback, log, etc.)
        }
    }

    public void deletarReservaPorId(int idReservas) {
        String sql = "DELETE FROM reservas WHERE idReservas = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idReservas);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Reserva deletada com sucesso!");
            } else {
                System.out.println("Reserva não encontrada.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar reserva: " + e.getMessage());
            // Tratar o erro de forma mais adequada (rollback, log, etc.)
        }
    }

    public void deletarReservasPorCliente(int idCliente) {
        String sql = "DELETE FROM reservas WHERE idCliente = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Reservas do cliente deletadas com sucesso!");
            } else {
                System.out.println("Nenhuma reserva encontrada para o cliente.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar reservas: " + e.getMessage());
            // Tratar o erro de forma mais adequada (rollback, log, etc.)
        }
    }

}
