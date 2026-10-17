package biblioteca;

import java.util.List;
import java.util.Scanner;

/**
 * Sistema de Biblioteca em Java.
 * Autor: Thyago Ferreira (https://github.com/thyagodev021)
 */
public class Main {
    private static final Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca("livros.csv");
        System.out.println("📚 Biblioteca em Java - por Thyago Ferreira");

        int opcao;
        do {
            System.out.println("\n[1] Cadastrar  [2] Listar  [3] Buscar  [4] Emprestar");
            System.out.println("[5] Devolver  [6] Remover  [0] Sair");
            opcao = lerInt("Escolha: ");

            try {
                switch (opcao) {
                    case 1 -> {
                        String titulo = lerTexto("Título: ");
                        String autor = lerTexto("Autor: ");
                        Livro livro = biblioteca.adicionar(titulo, autor);
                        System.out.println("Cadastrado: " + livro);
                    }
                    case 2 -> mostrar(biblioteca.listar());
                    case 3 -> mostrar(biblioteca.buscar(lerTexto("Buscar por título ou autor: ")));
                    case 4 -> {
                        biblioteca.emprestar(lerInt("Número do livro: "));
                        System.out.println("Livro emprestado!");
                    }
                    case 5 -> {
                        biblioteca.devolver(lerInt("Número do livro: "));
                        System.out.println("Livro devolvido!");
                    }
                    case 6 -> {
                        biblioteca.remover(lerInt("Número do livro: "));
                        System.out.println("Livro removido!");
                    }
                    case 0 -> System.out.println("Até logo!");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("⚠️  " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void mostrar(List<Livro> livros) {
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return;
        }
        long disponiveis = livros.stream().filter(l -> !l.isEmprestado()).count();
        System.out.println("\n--- " + livros.size() + " livro(s), " + disponiveis + " disponível(is) ---");
        livros.forEach(System.out::println);
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return entrada.nextLine().trim();
    }

    private static int lerInt(String mensagem) {
        try {
            return Integer.parseInt(lerTexto(mensagem));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
