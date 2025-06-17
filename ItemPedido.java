public class ItemPedido {
    private Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }

    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return produto.getId() + ";" + quantidade;
    }

    public static ItemPedido fromString(String linha, ProdutoServico produtoServico) {
        String[] parts = linha.split(";");
        Produto produto = produtoServico.buscarProdutoPorId(Integer.parseInt(parts[0]));
        int qtd = Integer.parseInt(parts[1]);
        return new ItemPedido(produto, qtd);
    }
}