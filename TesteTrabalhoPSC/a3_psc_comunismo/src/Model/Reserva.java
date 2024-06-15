package Model;

public class Reserva {

    private int idReserva;
    private int idCliente;
    private String origem;
    private String destino;
    private String dataViagem;


    public Reserva(int idCliente, String origem, String destino, String dataViagem) {

        this.idCliente = idCliente;
        this.origem = origem;
        this.destino = destino;
        this.dataViagem = dataViagem;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDataViagem() {
        return  dataViagem;
    }

    public void setDataViagem(String dataViagem) {
        this.dataViagem = dataViagem;
    }

    @Override
    public String toString() {
        return "\nId = " + idReserva + "\nOrigem: " + origem + "\nDestino: " + destino + "\nData de viagem: " + dataViagem + "\n--------------";
    }
}