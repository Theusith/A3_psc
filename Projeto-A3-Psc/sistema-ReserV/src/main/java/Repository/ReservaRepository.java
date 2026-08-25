package Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import Conexao.Conexao;
import Model.Reserva;

/**
 * Classe responsável por toda a comunicação com o banco de dados
 * relacionada à tabela RESERVAS.
 */
public class ReservaRepository {

    private static final DateTimeFormatter FORMATO_ENTRADA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final SimpleDateFormat FORMATO_SAIDA = new SimpleDateFormat("dd/MM/yyyy");

    public void inserir(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO RESERVAS (idCliente, origem, destino, dataViagem) VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, reserva.getIdCliente());
            stmt.setString(2, reserva.getOrigem());
            stmt.setString(3, reserva.getDestino());

            LocalDate localDate = LocalDate.parse(reserva.getDataViagem(), FORMATO_ENTRADA);
            stmt.setDate(4, Date.valueOf(localDate));

            stmt.executeUpdate();
        }
    }

    public List<Reserva> buscarPorCliente(int idCliente) throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE idCliente = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String origem = rs.getString("origem");
                String destino = rs.getString("destino");
                Date dataViagemSql = rs.getDate("dataViagem");
                int idReserva = rs.getInt("idReservas");
                String dataViagem = FORMATO_SAIDA.format(dataViagemSql);

                Reserva reserva = new Reserva(rs.getInt("idCliente"), origem, destino, dataViagem);
                reserva.setIdReserva(idReserva);
                reservas.add(reserva);
            }
        }
        return reservas;
    }

    public boolean atualizar(int idCliente, int idReservas, Reserva reservaAtualizada) throws SQLException {
        String sql = "UPDATE reservas SET origem = ?, destino = ?, dataViagem = ? WHERE idReservas = ? AND idCLiente = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, reservaAtualizada.getOrigem());
            stmt.setString(2, reservaAtualizada.getDestino());

            LocalDate localDate = LocalDate.parse(reservaAtualizada.getDataViagem(), FORMATO_ENTRADA);
            stmt.setDate(3, Date.valueOf(localDate));
            stmt.setInt(4, idReservas);
            stmt.setInt(5, idCliente);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deletarPorId(int idReservas) throws SQLException {
        String sql = "DELETE FROM reservas WHERE idReservas = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idReservas);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deletarPorCliente(int idCliente) throws SQLException {
        String sql = "DELETE FROM reservas WHERE idCliente = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            return stmt.executeUpdate() > 0;
        }
    }
}