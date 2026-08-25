package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um cliente do sistema, que herda características básicas de uma pessoa.
 */
public class Cliente extends Pessoa {
    private List<Reserva> reservas; // Lista de reservas feitas pelo cliente.

    /**
     * Construtor para inicializar um objeto Cliente com nome, CPF, email e senha.
     *
     * @param nome  O nome do cliente.
     * @param cpf   O CPF do cliente.
     * @param email O email do cliente.
     * @param senha A senha do cliente.
     */
    public Cliente(String nome, String cpf, String email, String senha) {
        super(nome, cpf, email, senha);  // Chama o construtor da superclasse Pessoa.
        this.reservas = new ArrayList<>(); // Inicializa a lista de reservas como uma ArrayList vazia.
    }

    /**
     * Método para exibir as reservas do cliente. Caso não haja reservas, imprime uma mensagem indicando isso.
     */
    public void getReservas() {
        if (!reservas.isEmpty()) {
            for (Reserva r : reservas) {
                System.out.println(r); // Exibe cada reserva usando o método toString() de Reserva.
            }
        } else {
            System.out.println("Nenhuma reserva encontrada");
        }
    }
    /**
     * Sobrescreve o método toString() para retornar uma representação em string do objeto Cliente.
     *
     * @return Uma string contendo nome, CPF e email do cliente.
     */
    @Override
    public String toString() {
        return "Nome:" + this.nome + "\nCpf: " + this.cpf + "\nEmail: " + this.email;
    }
}