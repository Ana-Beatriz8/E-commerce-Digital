import java.util.Scanner;

public class FormaPagamento {

    private static Scanner scanner = new Scanner(System.in);

    // Método estático para realizar pagamento, interagindo via terminal
    public static boolean realizarPagamento(double valorTotal) {
        System.out.println("\n=== FORMAS DE PAGAMENTO ===");
        System.out.println("1 - Cartão de Crédito");
        System.out.println("2 - Débito");
        System.out.println("3 - Boleto");
        System.out.println("0 - Cancelar pagamento");
        System.out.print("Escolha a forma de pagamento: ");

        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                System.out.print("Digite o número do cartão de crédito: ");
                String numCartaoCredito = scanner.nextLine();
                // Aqui você pode validar o número ou dados (exemplo simplificado)
                System.out.println("Cartão de crédito cadastrado: " + numCartaoCredito);
                break;

            case "2":
                System.out.print("Digite o número do cartão de débito: ");
                String numCartaoDebito = scanner.nextLine();
                System.out.println("Cartão de débito cadastrado: " + numCartaoDebito);
                break;

            case "3":
                System.out.print("Digite seu CPF para gerar boleto: ");
                String cpf = scanner.nextLine();
                System.out.println("Boleto gerado para CPF: " + cpf);
                break;

            case "0":
                System.out.println("Pagamento cancelado pelo usuário.");
                return false;

            default:
                System.out.println("Opção inválida.");
                return false;
        }

        System.out.printf("Confirmar pagamento de R$ %.2f? (S/N): ", valorTotal);
        String confirma = scanner.nextLine();

        if (confirma.equalsIgnoreCase("S")) {
            System.out.println("Pagamento aprovado com sucesso!");
            return true;
        } else {
            System.out.println("Pagamento cancelado.");
            return false;
        }
    }
}