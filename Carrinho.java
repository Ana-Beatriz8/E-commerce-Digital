import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private List<ItemPedido> itens = new ArrayList<>();

    public void adicionarItem(ItemPedido item) {
        // Se o produto já existe no carrinho, aumenta a quantidade
        for (ItemPedido i : itens) {
            if (i.getProduto().getId() == item.getProduto().getId()) {
                int novaQtd = i.getQuantidade() + item.getQuantidade();
                itens.remove(i);
                itens.add(new ItemPedido(item.getProduto(), novaQtd));
                return;
            }
        }
        itens.add(item);
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void limpar() {
        itens.clear();
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemPedido i : itens) {
            total += i.getSubtotal();
        }
        return total;
    }

    public void exibirResumo() {
        if (itens.isEmpty()) {
            System.out.println("Carrinho vazio.");
            return;
        }
        System.out.println("\nItens no carrinho:");
        for (ItemPedido i : itens) {
            System.out.println(i.getProduto().getNome() + " - Quantidade: " + i.getQuantidade() + " - Subtotal: R$ " + String.format("%.2f", i.getSubtotal()));
        }
        System.out.println("Total: R$ " + String.format("%.2f", calcularTotal()));
    }
}