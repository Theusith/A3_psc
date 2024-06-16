/**
 * A classe Main contém o ponto de entrada para a aplicação do sistema de reservas.
 * Ela cria uma instância de SistemaReservas e chama o seu método executar para iniciar a aplicação.
 */
package Controller;
public class Main {
    /**
     * O método main é o ponto de entrada para a execução da aplicação Java.
     * Ele inicializa o sistema de reservas e inicia a sua execução.
     *
     * @param args Argumentos da linha de comando (não utilizados nesta aplicação).
     */
    public static void main(String[] args) {
        // Cria uma instância de SistemaReservas
        SistemaReservas sistema = new SistemaReservas();
        // Executa o sistema de reservas
        sistema.executar();
    }
}