package Model;

/**
 * Representa uma pessoa no sistema de reservas.
 * Esta é uma classe abstrata que serve como base para outras classes específicas, como Cliente e Administrador.
 */
public class Pessoa {
    protected int iD;
    protected String nome;
    protected String cpf;
    protected String email;
    protected String senha;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    protected String tipo;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    protected String matricula;


    public Pessoa(String nome, String cpf, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }

    public void setId(int iD) {
        this.iD = iD;
    }


    public int getId() {
        return iD;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getSenha() {
        return senha;
    }


    public void setSenha(String senha) {
        this.senha = senha;
    }


    @Override
    public String toString() {
        return "Nome: " + nome + "\nId: " + iD;
    }
}
