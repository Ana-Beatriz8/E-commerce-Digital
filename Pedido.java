import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private double total;

    public Pedido(int id, Cliente cliente, List<ItemPedido> itens, double total) {
        this.id = id;
        this.cliente = cliente;
        this.itens = itens;
        this.total = total;
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItens() { return itens; }
    public double getTotal() { return total; }

    public void exibirResumo() {
        System.out.println("Pedido #" + id + " - Total: R$ " + String.format("%.2f", total));
        for (ItemPedido item : itens) {
            System.out.println("  " + item.getProduto().getNome() + " x " + item.getQuantidade() + " = R$ " + String.format("%.2f", item.getSubtotal()));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(";").append(cliente.getId()).append(";").append(total).append(";");
        for (int i = 0; i < itens.size(); i++) {
            sb.append(itens.get(i).toString());
            if (i < itens.size() - 1) sb.append("|");
        }
        return sb.toString();
    }

    public static Pedido fromString(String linha, ClienteServico clienteServico, ProdutoServico produtoServico) {
        String[] parts = linha.split(";");
        int id = Integer.parseInt(parts[0]);
        Cliente cliente = clienteServico.buscarClientePorId(Integer.parseInt(parts[1]));
        double total = Double.parseDouble(parts[2]);

        String[] itensStr = parts[3].split("\\|");
        List<ItemPedido> itens = new java.util.ArrayList<>();
        for (String itemStr : itensStr) {
            itens.add(ItemPedido.fromString(itemStr, produtoServico));
        }
        return new Pedido(id, cliente, itens, total);
    }
}