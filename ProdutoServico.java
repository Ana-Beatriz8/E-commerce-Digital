import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class ProdutoServico {
    private List<Produto> produtos = new ArrayList<>();
    private final String arquivo = "produtos.txt";

    public void carregarProdutos() {
        produtos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                produtos.add(Produto.fromString(linha));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo produtos.txt não encontrado. Criando novo.");
            salvarProdutos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvarProdutos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
            for (Produto p : produtos) {
                pw.println(p.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listarProdutos() {
        System.out.println("\nProdutos disponíveis:");
        for (Produto p : produtos) {
            System.out.println(p.getId() + " - " + p.getNome() + " - " + p.getDescricao() + " - R$ " + String.format("%.2f", p.getPreco()) + " - Estoque: " + p.getEstoque());
        }
    }

    public Produto buscarProdutoPorId(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public void atualizarEstoque(List<ItemPedido> itens) {
        for (ItemPedido item : itens) {
            Produto p = buscarProdutoPorId(item.getProduto().getId());
            if (p != null) {
                p.setEstoque(p.getEstoque() - item.getQuantidade());
            }
        }
        salvarProdutos();
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}