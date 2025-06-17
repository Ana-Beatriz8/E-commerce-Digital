public class Cliente {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    public Cliente(int id, String nome, String email, String senha, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getEndereco() { return endereco; }

    @Override
    public String toString() {
        return id + ";" + nome + ";" + email + ";" + senha + ";" + endereco;
    }

    public static Cliente fromString(String linha) {
        String[] parts = linha.split(";");
        return new Cliente(
            Integer.parseInt(parts[0]),
            parts[1],
            parts[2],
            parts[3],
            parts[4]
        );
    }
}