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
    protected String tipo;
    protected String matricula;
    /**
     * Obtém o tipo da pessoa.
     * @return Tipo da pessoa.
     */
    public String getTipo() {
        return tipo;
    }
    /**
     * Define o tipo da pessoa.
     * @param tipo Novo tipo da pessoa.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    /**
     * Obtém a matrícula da pessoa.
     * @return Matrícula da pessoa.
     */
    public String getMatricula() {
        return matricula;
    }
    /**
     * Define a matrícula da pessoa.
     * @param matricula Nova matrícula da pessoa.
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Construtor da classe Pessoa.
     * @param nome Nome da pessoa.
     * @param cpf CPF da pessoa.
     * @param email E-mail da pessoa.
     * @param senha Senha da pessoa.
     */
    public Pessoa(String nome, String cpf, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }
    /**
     * Define o ID da pessoa.
     * @param iD ID da pessoa.
     */
    public void setId(int iD) {
        this.iD = iD;
    }

    /**
     * Obtém o ID da pessoa.
     * @return ID da pessoa.
     */
    public int getId() {
        return iD;
    }
    /**
     * Obtém o nome da pessoa.
     * @return Nome da pessoa.
     */
    public String getNome() {
        return nome;
    }
    /**
     * Define o nome da pessoa.
     * @param nome Novo nome da pessoa.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o CPF da pessoa.
     * @return CPF da pessoa.
     */
    public String getCpf() {
        return cpf;
    }
    /**
     * Define o CPF da pessoa.
     * @param cpf Novo CPF da pessoa.
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Obtém o e-mail da pessoa.
     * @return E-mail da pessoa.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Define o e-mail da pessoa.
     * @param email Novo e-mail da pessoa.
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém a senha da pessoa.
     * @return Senha da pessoa.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha da pessoa.
     * @param senha Nova senha da pessoa.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Representação em formato de String da Pessoa.
     * @return Representação textual da Pessoa contendo Nome e ID.
     */
    @Override
    public String toString() {
        return "Nome: " + nome + "\nId: " + iD;
    }
}
