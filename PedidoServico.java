import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class PedidoServico {
    private List<Pedido> pedidos = new ArrayList<>();
    private final String arquivo = "pedidos.txt";

    public void carregarPedidos() {
        pedidos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            ClienteServico clienteServico = new ClienteServico();
            ProdutoServico produtoServico = new ProdutoServico();

            // Precisa carregar clientes e produtos para construir pedidos
            clienteServico.carregarClientes();
            produtoServico.carregarProdutos();

            while ((linha = br.readLine()) != null) {
                pedidos.add(Pedido.fromString(linha, clienteServico, produtoServico));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo pedidos.txt não encontrado. Criando novo.");
            salvarPedidos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvarPedidos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
            for (Pedido p : pedidos) {
                pw.println(p.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
        salvarPedidos();
    }

    public List<Pedido> buscarPedidosPorCliente(int clienteId) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.getCliente().getId() == clienteId) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public int getProximoId() {
        return pedidos.size() + 1;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
}
