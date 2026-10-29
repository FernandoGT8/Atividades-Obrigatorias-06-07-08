public abstract class ItemBiblioteca {
    private final String codigo;
    private final String titulo;
    private boolean disponivel;

    public ItemBiblioteca(String codigo, String titulo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.disponivel = true;
    }

    public abstract String tipo();

    public abstract int prazo();

    public abstract double multaPorDia();

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    void marcarComoEmprestado() {
        disponivel = false;
    }

    void marcarComoDisponivel() {
        disponivel = true;
    }

    public String descricao() {
        String situacao = disponivel ? "disponível" : "emprestado";
        return tipo() + " | " + codigo + " | " + titulo + " | " + situacao;
    }
}
