import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Sistema {
    private ClienteServico clienteServico = new ClienteServico();
    private ProdutoServico produtoServico = new ProdutoServico();
    private PedidoServico pedidoServico = new PedidoServico();
    private Carrinho carrinho = new Carrinho();

    private Cliente clienteLogado = null;
    private Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        clienteServico.carregarClientes();
        produtoServico.carregarProdutos();
        pedidoServico.carregarPedidos();

        System.out.println("=== Bem-vindo à Loja Virtual de Livros ===");

        boolean sair = false;
        while (!sair) {
            if (clienteLogado == null) {
                menuInicial();
            } else {
                menuCliente();
            }
        }
    }

    private void menuInicial() {
        System.out.println("\n1 - Login");
        System.out.println("2 - Cadastrar cliente");
        System.out.println("3 - Listar produtos");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
        String escolha = scanner.nextLine();

        switch (escolha) {
            case "1":
                login();
                break;
            case "2":
                cadastrarCliente();
                break;
            case "3":
                produtoServico.listarProdutos();
                break;
            case "0":
                System.out.println("Obrigado por visitar a loja!");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void menuCliente() {
        System.out.println("\nCliente: " + clienteLogado.getNome());
        System.out.println("1 - Listar produtos");
        System.out.println("2 - Adicionar produto ao carrinho");
        System.out.println("3 - Ver carrinho");
        System.out.println("4 - Finalizar pedido");
        System.out.println("5 - Ver histórico de pedidos");
        System.out.println("6 - Logout");
        System.out.print("Escolha: ");
        String escolha = scanner.nextLine();

        switch (escolha) {
            case "1":
                produtoServico.listarProdutos();
                break;
            case "2":
                adicionarProdutoAoCarrinho();
                break;
            case "3":
                carrinho.exibirResumo();
                break;
            case "4":
                finalizarPedido();
                break;
            case "5":
                mostrarHistoricoPedidos();
                break;
            case "6":
                logout();
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void login() {
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Cliente c = clienteServico.autenticar(email, senha);
        if (c != null) {
            clienteLogado = c;
            System.out.println("Login realizado com sucesso! Bem-vindo, " + c.getNome());
        } else {
            System.out.println("Usuário ou senha inválidos.");
        }
    }

    private void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        clienteServico.cadastrarCliente(nome, email, senha, endereco);
        System.out.println("Cadastro realizado com sucesso!");
    }

    private void adicionarProdutoAoCarrinho() {
        produtoServico.listarProdutos();
        System.out.print("Digite o ID do produto para adicionar ao carrinho: ");
        int idProduto;
        try {
            idProduto = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        Produto produto = produtoServico.buscarProdutoPorId(idProduto);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.print("Quantidade: ");
        int quantidade;
        try {
            quantidade = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Quantidade inválida.");
            return;
        }

        if (quantidade <= 0) {
            System.out.println("Quantidade deve ser maior que zero.");
            return;
        }

        if (produto.getEstoque() < quantidade) {
            System.out.println("Estoque insuficiente.");
            return;
        }

        carrinho.adicionarItem(new ItemPedido(produto, quantidade));
        System.out.println("Produto adicionado ao carrinho.");
    }

    private void finalizarPedido() {
        if (carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho vazio.");
            return;
        }

        double total = carrinho.calcularTotal();
        boolean pagamentoRealizado = FormaPagamento.realizarPagamento(total);

        if (!pagamentoRealizado) {
            System.out.println("Pagamento não realizado. Pedido cancelado.");
            return;
        }

        Pedido pedido = new Pedido(
                pedidoServico.getProximoId(),
                clienteLogado,
                new ArrayList<>(carrinho.getItens()),
                total
        );

        produtoServico.atualizarEstoque(pedido.getItens());
        pedidoServico.adicionarPedido(pedido);
        carrinho.limpar();

        System.out.println("Pedido finalizado com sucesso!");
    }

    private void mostrarHistoricoPedidos() {
        List<Pedido> pedidosCliente = pedidoServico.buscarPedidosPorCliente(clienteLogado.getId());
        if (pedidosCliente.isEmpty()) {
            System.out.println("Nenhum pedido realizado ainda.");
            return;
        }

        System.out.println("Histórico de pedidos:");
        for (Pedido p : pedidosCliente) {
            p.exibirResumo();
            System.out.println("---------------------");
        }
    }

    private void logout() {
        clienteLogado = null;
        carrinho.limpar();
        System.out.println("Logout realizado.");
    }
}

