package Controller;

import Model.Cliente;
import Model.Pessoa;
import Model.Reserva;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Scanner;

public class SistemaReservas {

    private GerenciadorContas gerenciadorContas;
    private GerenciadorReservas gerenciadorReservas;
    private Scanner scanner;

    public SistemaReservas() {
        this.gerenciadorContas = new GerenciadorContas();
        this.gerenciadorReservas = new GerenciadorReservas();
        this.scanner = new Scanner(System.in);
    }

    public void executar() {
        boolean sair = false;
        while (!sair) {
            System.out.println("=== Sistema de Reservas ===");
            System.out.println("1. Fazer login");
            System.out.println("2. Criar conta");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            char opcao = scanner.next().charAt(0);
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case '1':
                    fazerLogin();
                    break;
                case '2':
                    criarConta();
                    break;
                case '3':
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void fazerLogin() {
        System.out.println("=== Fazer Login ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        // Verificar se as credenciais correspondem a alguma conta
        Pessoa pessoaLogada = gerenciadorContas.autenticarPessoa(email, senha);
        if (pessoaLogada != null) {
            System.out.println("Login bem-sucedido!");
            if (Objects.equals(pessoaLogada.getTipo(), "Administrador")) {
                exibirMenuAdministrador();
            } else if (Objects.equals(pessoaLogada.getTipo(), "Cliente")) {
                exibirMenuPrincipal(pessoaLogada);
            }
        } else {
            System.out.println("Email ou senha incorretos. Tente novamente.");
        }
    }

    private void criarConta() {
        System.out.println("=== Criar Conta ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        String tipo = "Cliente";
        Cliente l = new Cliente(nome, cpf, email, senha);
        l.setTipo(tipo);

        new GerenciadorContas().cadastrarCliente(l);
    }

    private void exibirMenuPrincipal(Pessoa pessoaLogada) {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Criar nova reserva");
            System.out.println("2. Visualizar reservas");
            System.out.println("3. Editar reserva");
            System.out.println("4. Excluir reserva");
            System.out.println("5. Excluir conta");
            System.out.println("6. Logout");
            System.out.print("Escolha uma opção: ");
            char opcao = scanner.next().charAt(0);
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case '1':
                    criarNovaReserva(pessoaLogada);
                    break;
                case '2':
                    System.out.println(gerenciadorReservas.visualizarReservasPorCliente(pessoaLogada.getId()));
                    break;
                case '3':
                    editarReserva(pessoaLogada);
                    break;
                case '4':
                    excluirReserva();
                    break;
                case '5':
                    //excluirConta(cliente);
                    gerenciadorContas.deletarClientePorId(pessoaLogada.getId());
                    gerenciadorReservas.deletarReservasPorCliente(pessoaLogada.getId());
                    sair = true; // Forçar logout após excluir conta
                    break;
                case '6':
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void exibirMenuAdministrador() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n=== Menu Administrador ===");
            System.out.println("1. Alterar dados pessoais de um cliente por ID");
            System.out.println("2. Exibir reservas de um cliente");
            System.out.println("3. Excluir a conta de um cliente");
            System.out.println("4. Visualizar clientes");
            System.out.println("5. Logout");
            System.out.print("Escolha uma opção: ");


            char opcao = scanner.next().charAt(0);


            switch (opcao) {
                case '1':
                    alterarDadosClientePorId();
                    break;
                case '2':
                    System.out.println("Digite o Id do cliente:");
                    int idConsultado = scanner.nextInt();
                    if(gerenciadorContas.obterClientePorId(idConsultado) != null){
                        System.out.println(gerenciadorReservas.visualizarReservasPorCliente(idConsultado));
                    }else{
                        System.out.println("Id de cliente inexistente.");
                    }
                    break;
                case '3':
                    System.out.println("Digite o ID:");
                    int id = scanner.nextInt();
                    gerenciadorContas.deletarClientePorId(id);
                    gerenciadorReservas.deletarReservasPorCliente(id);
                    break;
                case '4':
                    System.out.println(gerenciadorContas.visualizarClientes());;
                    break;
                case '5':
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void alterarDadosClientePorId() {
        System.out.println("=== Alterar Dados de Cliente ===");
        System.out.print("ID do cliente: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        Cliente clienteParaAlterar = gerenciadorContas.obterClientePorId(idCliente);
        if (clienteParaAlterar == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }else{
            System.out.println("Alterando dados de: " + clienteParaAlterar.getNome());
            System.out.print("Novo nome: ");
            String novoNome = scanner.nextLine();
            System.out.print("Novo CPF: ");
            String novoCpf = scanner.nextLine();
            System.out.print("Novo email: ");
            String novoEmail = scanner.nextLine();
            System.out.print("Nova senha: ");
            String novaSenha = scanner.nextLine();

            clienteParaAlterar.setNome(novoNome);
            clienteParaAlterar.setCpf(novoCpf);
            clienteParaAlterar.setEmail(novoEmail);
            clienteParaAlterar.setSenha(novaSenha);

            gerenciadorContas.alterarClientePorId(idCliente, clienteParaAlterar);

            System.out.println("Dados alterados com sucesso!");
        }


    }

    private void criarNovaReserva(Pessoa pessoaLogada) {
        boolean sair = false;
        while (!sair) {

            System.out.println("=== Criar Nova Reserva ===");
            System.out.print("Origem: ");
            String origem = scanner.nextLine();
            System.out.print("Destino: ");
            String destino = scanner.nextLine();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            System.out.print("Data de Viagem (DD/MM/AAAA): ");
            String dataViagem = scanner.nextLine();


            Reserva novaReserva = new Reserva(pessoaLogada.getId(), origem, destino, dataViagem);
            gerenciadorReservas.adicionarReserva(novaReserva);

            System.out.print("Deseja fazer mais alguma reserva? (S/N): ");
            String confirmacao = scanner.nextLine();
            if (confirmacao.equalsIgnoreCase("N")) {
                sair = true;
            }
        }
    }

    private void editarReserva(Pessoa pessoaLogada) {


        System.out.println("=== Editar Reserva ===");
        System.out.print("ID da Reserva: ");
        int idReserva = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        // Solicitar novos detalhes da reserva ao cliente
        System.out.print("Nova Origem: ");
        String novaOrigem = scanner.nextLine();
        System.out.print("Novo Destino: ");
        String novoDestino = scanner.nextLine();
        System.out.print("Nova Data de Viagem (DD/MM/AAAA): ");
        String novaDataViagem = scanner.nextLine();
        Reserva reservaParaEditar = new Reserva(pessoaLogada.getId(), novaOrigem, novoDestino, novaDataViagem);


        // Atualizar os detalhes da reserva
        reservaParaEditar.setOrigem(novaOrigem);
        reservaParaEditar.setDestino(novoDestino);
        reservaParaEditar.setDataViagem(novaDataViagem);
        gerenciadorReservas.editarReserva(pessoaLogada.getId(), idReserva, reservaParaEditar);

        return ;
    }

    private void excluirReserva() {
        System.out.println("=== Excluir Reserva ===");
        System.out.print("ID da Reserva: ");
        int idReserva = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        gerenciadorReservas.deletarReservaPorId(idReserva);
    }

}





