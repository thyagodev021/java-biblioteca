package biblioteca;

/** Representa um livro da biblioteca. */
public class Livro {
    private final int id;
    private final String titulo;
    private final String autor;
    private boolean emprestado;

    public Livro(int id, String titulo, String autor, boolean emprestado) {
        this.id = id;
        this.titulo = limpar(titulo);
        this.autor = limpar(autor);
        this.emprestado = emprestado;
    }

    /** O arquivo usa ';' como separador, então trocamos por ',' para não quebrar. */
    private static String limpar(String texto) {
        return texto.replace(';', ',').trim();
    }

    /** Cria um livro a partir de uma linha do arquivo: id;titulo;autor;emprestado */
    public static Livro deCsv(String linha) {
        String[] partes = linha.split(";", -1);
        if (partes.length != 4) {
            throw new IllegalArgumentException("Linha inválida: " + linha);
        }
        return new Livro(Integer.parseInt(partes[0]), partes[1], partes[2], Boolean.parseBoolean(partes[3]));
    }

    public String paraCsv() {
        return id + ";" + titulo + ";" + autor + ";" + emprestado;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public boolean isEmprestado() { return emprestado; }
    public void setEmprestado(boolean emprestado) { this.emprestado = emprestado; }

    @Override
    public String toString() {
        return String.format("#%d | %-30s | %-20s | %s",
                id, titulo, autor, emprestado ? "Emprestado" : "Disponível");
    }
}
