package biblioteca;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Guarda os livros em memória e salva tudo em um arquivo de texto (CSV). */
public class Biblioteca {
    private final Path arquivo;
    private final List<Livro> livros = new ArrayList<>();
    private int proximoId = 1;

    public Biblioteca(String caminhoArquivo) {
        this.arquivo = Path.of(caminhoArquivo);
        carregar();
    }

    private void carregar() {
        if (!Files.exists(arquivo)) {
            return;
        }
        try {
            for (String linha : Files.readAllLines(arquivo, StandardCharsets.UTF_8)) {
                if (linha.isBlank()) {
                    continue;
                }
                Livro livro = Livro.deCsv(linha);
                livros.add(livro);
                proximoId = Math.max(proximoId, livro.getId() + 1);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Aviso: não foi possível ler " + arquivo + " (" + e.getMessage() + ")");
        }
    }

    private void salvar() {
        List<String> linhas = livros.stream().map(Livro::paraCsv).toList();
        try {
            Files.write(arquivo, linhas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public Livro adicionar(String titulo, String autor) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título não pode ser vazio.");
        }
        String autorFinal = (autor == null || autor.isBlank()) ? "Autor desconhecido" : autor;
        Livro livro = new Livro(proximoId++, titulo, autorFinal, false);
        livros.add(livro);
        salvar();
        return livro;
    }

    public List<Livro> listar() {
        return List.copyOf(livros);
    }

    /** Busca por parte do título ou do autor, sem diferenciar maiúsculas de minúsculas. */
    public List<Livro> buscar(String termo) {
        String t = termo.toLowerCase();
        return livros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(t)
                        || l.getAutor().toLowerCase().contains(t))
                .toList();
    }

    private Livro porId(int id) {
        return livros.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Livro #" + id + " não encontrado."));
    }

    public void emprestar(int id) {
        Livro livro = porId(id);
        if (livro.isEmprestado()) {
            throw new IllegalStateException("Esse livro já está emprestado.");
        }
        livro.setEmprestado(true);
        salvar();
    }

    public void devolver(int id) {
        Livro livro = porId(id);
        if (!livro.isEmprestado()) {
            throw new IllegalStateException("Esse livro não está emprestado.");
        }
        livro.setEmprestado(false);
        salvar();
    }

    public void remover(int id) {
        livros.remove(porId(id));
        salvar();
    }
}
