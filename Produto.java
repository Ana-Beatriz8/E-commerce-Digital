public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;    

    public Produto(int id, String nome, String descricao, double preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getPreco() { return preco; }
    public int getEstoque() { return estoque; }

    public void setEstoque(int estoque) { this.estoque = estoque; }

    @Override
    public String toString() {
        return id + ";" + nome + ";" + descricao + ";" + preco + ";" + estoque;
    }

    public static Produto fromString(String linha) {
        String[] parts = linha.split(";");
        return new Produto(
            Integer.parseInt(parts[0]),
            parts[1],
            parts[2],
            Double.parseDouble(parts[3]),
            Integer.parseInt(parts[4])
        );
    }
}