import java.time.LocalDate;

public class Biblioteca {
    private static final int CAPACIDADE_ACERVO = 100;
    private static final int CAPACIDADE_EMPRESTIMOS = 500;

    private final ItemBiblioteca[] acervo = new ItemBiblioteca[CAPACIDADE_ACERVO];
    private int totalItens = 0;

    private final Emprestimo[] emprestimos = new Emprestimo[CAPACIDADE_EMPRESTIMOS];
    private int totalEmprestimos = 0;

    public void cadastrarItem(ItemBiblioteca item) {
        if (totalItens == acervo.length) {
            throw new IllegalStateException("Acervo cheio");
        }
        acervo[totalItens] = item;
        totalItens++;
    }

    public Emprestimo emprestar(Usuario usuario, ItemBiblioteca item, LocalDate dataRetirada) {
        if (!item.isDisponivel()) {
            throw new IllegalStateException("\"" + item.getTitulo() + "\" já está emprestado");
        }
        if (!usuario.podeEmprestar()) {
            throw new IllegalStateException(usuario.getNome() + " atingiu o limite de "
                    + usuario.limiteItens() + " itens");
        }
        if (totalEmprestimos == emprestimos.length) {
            throw new IllegalStateException("Limite de empréstimos registrados atingido");
        }

        Emprestimo emprestimo = new Emprestimo(item, usuario, dataRetirada);
        emprestimos[totalEmprestimos] = emprestimo;
        totalEmprestimos++;

        item.marcarComoEmprestado();
        usuario.registrarEmprestimo();
        return emprestimo;
    }

    public double devolver(ItemBiblioteca item, LocalDate dataDevolucao) {
        for (int i = 0; i < totalEmprestimos; i++) {
            Emprestimo emprestimo = emprestimos[i];
            if (emprestimo.isAberto() && emprestimo.getItem() == item) {
                double multa = emprestimo.registrarDevolucao(dataDevolucao);
                emprestimo.getUsuario().registrarDevolucao();
                item.marcarComoDisponivel();
                return multa;
            }
        }
        throw new IllegalStateException("\"" + item.getTitulo() + "\" não está emprestado");
    }

    public void listarAcervo() {
        for (int i = 0; i < totalItens; i++) {
            System.out.println(acervo[i].descricao());
        }
    }
}
