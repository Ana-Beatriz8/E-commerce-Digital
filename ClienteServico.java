import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class ClienteServico {
    private List<Cliente> clientes = new ArrayList<>();
    private final String arquivo = "clientes.txt";

    public void carregarClientes() {
        clientes.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                clientes.add(Cliente.fromString(linha));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo clientes.txt não encontrado. Criando novo.");
            salvarClientes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvarClientes() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
            for (Cliente c : clientes) {
                pw.println(c.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarCliente(String nome, String email, String senha, String endereco) {
        int novoId = clientes.size() + 1;
        Cliente cliente = new Cliente(novoId, nome, email, senha, endereco);
        clientes.add(cliente);
        salvarClientes();
    }

    public Cliente autenticar(String email, String senha) {
        for (Cliente c : clientes) {
            if (c.getEmail().equals(email) && c.getSenha().equals(senha)) {
                return c;
            }
        }
        return null;
    }

    public Cliente buscarClientePorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
