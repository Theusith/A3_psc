package Model;
/**
 * Representa uma reserva de viagem.
 */
public class Reserva {
    /**
     * ID da reserva.
     */
    private int idReserva;
    /**
     * ID do cliente associado à reserva.
     */
    private int idCliente;
    /**
     * Local de origem da viagem.
     */
    private String origem;
    /**
     * Local de destino da viagem.
     */
    private String destino;
    /**
     * Data da viagem no formato String.
     */
    private String dataViagem;

    /**
     * Construtor para criar uma nova reserva.
     *
     * @param idCliente ID do cliente que fez a reserva
     * @param origem    Local de origem da viagem
     * @param destino   Local de destino da viagem
     * @param dataViagem Data da viagem no formato "dd/mm/aaaa"
     */
    public Reserva(int idCliente, String origem, String destino, String dataViagem) {

        this.idCliente = idCliente;
        this.origem = origem;
        this.destino = destino;
        this.dataViagem = dataViagem;
    }
    /**
     * Obtém o ID do cliente associado à reserva.
     *
     * @return O ID do cliente
     */
    public int getIdCliente() {
        return idCliente;
    }
    /**
     * Define o ID do cliente associado à reserva.
     *
     * @param idCliente O ID do cliente
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    /**
     * Obtém o ID da reserva.
     *
     * @return O ID da reserva
     */
    public int getIdReserva() {
        return idReserva;
    }
    /**
     * Define o ID da reserva.
     *
     * @param idReserva O ID da reserva
     */
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }
    /**
     * Obtém o local de origem da viagem.
     *
     * @return O local de origem
     */
    public String getOrigem() {
        return origem;
    }
    /**
     * Define o local de origem da viagem.
     *
     * @param origem O local de origem
     */
    public void setOrigem(String origem) {
        this.origem = origem;
    }
    /**
     * Obtém o local de destino da viagem.
     *
     * @return O local de destino
     */
    public String getDestino() {
        return destino;
    }
    /**
     * Define o local de destino da viagem.
     *
     * @param destino O local de destino
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }
    /**
     * Obtém a data da viagem.
     *
     * @return A data da viagem no formato "dd/mm/aaaa"
     */
    public String getDataViagem() {
        return  dataViagem;
    }
    /**
     * Define a data da viagem.
     *
     * @param dataViagem A data da viagem no formato "dd/mm/aaaa"
     */
    public void setDataViagem(String dataViagem) {
        this.dataViagem = dataViagem;
    }
    /**
     * Retorna uma representação em string da reserva.
     *
     * @return Uma string que representa a reserva com seu ID, origem, destino e data de viagem
     */
    @Override
    public String toString() {
        return "---------------------------------------" +
                "\nId = " + idReserva + "\nOrigem: " + origem + "\nDestino: " + destino + "\nData de viagem: " + dataViagem;
    }
}