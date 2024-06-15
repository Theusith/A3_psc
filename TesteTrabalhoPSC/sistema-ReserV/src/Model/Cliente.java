package Model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {
    private List<Reserva> reservas;


    public Cliente(String nome, String cpf, String email, String senha) {
        super(nome, cpf, email, senha);
        this.reservas = new ArrayList<>();
    }

    public void getReservas() {
        if (!reservas.isEmpty()) {
            for (Reserva r : reservas) {
                System.out.println(r);
            }
        } else {
            System.out.println("Nenhuma reserva encontrada");
        }
    }

    @Override
    public String toString() {
        return "Nome:" + this.nome + "\nCpf: " + this.cpf + "\nEmail: " + this.email;
    }
}