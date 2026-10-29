import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        LocalDate retirada = LocalDate.of(2026, 3, 2);

        System.out.println("=== 1. CADASTRO ===");
        Livro domCasmurro = new Livro("L001", "Dom Casmurro");
        Livro oCortico = new Livro("L002", "O Cortiço");
        Revista revCiencias = new Revista("R001", "Revista de Ciências");
        Revista revHistoria = new Revista("R002", "Revista de História");
        biblioteca.cadastrarItem(domCasmurro);
        biblioteca.cadastrarItem(oCortico);
        biblioteca.cadastrarItem(revCiencias);
        biblioteca.cadastrarItem(revHistoria);

        Aluno ana = new Aluno("Ana");
        Professor carlos = new Professor("Carlos");
        System.out.println("Itens cadastrados: 4 | Usuários: " + ana.getNome() + " (aluno), " + carlos.getNome() + " (professor)");

        System.out.println("\n=== 2. EMPRÉSTIMO BEM-SUCEDIDO ===");
        Emprestimo emprestimo = biblioteca.emprestar(ana, domCasmurro, retirada);
        System.out.println("OK: " + ana.getNome() + " pegou \"" + domCasmurro.getTitulo()
                + "\". Devolver até " + emprestimo.getDataDevolucaoPrevista());

        System.out.println("\n=== 3. EMPRÉSTIMO RECUSADO (LIMITE ATINGIDO) ===");
        biblioteca.emprestar(ana, oCortico, retirada);
        biblioteca.emprestar(ana, revCiencias, retirada);
        // Ana já tem 3 itens, que é o limite de um aluno
        try {
            biblioteca.emprestar(ana, revHistoria, retirada);
        } catch (IllegalStateException e) {
            System.out.println("RECUSADO: " + e.getMessage());
        }

        System.out.println("\n=== 4. DEVOLUÇÃO COM ATRASO ===");
        // Prazo do livro: 14 dias (16/03). Devolvido em 19/03: 3 dias de atraso x R$ 0,50
        double multa = biblioteca.devolver(domCasmurro, LocalDate.of(2026, 3, 19));
        System.out.println("Multa de " + ana.getNome() + ": " + String.format("R$ %.2f", multa));

        System.out.println("\n=== 5. NOVA CATEGORIA (DVD) SEM ALTERAR AS CLASSES EXISTENTES ===");
        Dvd filme = new Dvd("D001", "Filme de Exemplo");
        biblioteca.cadastrarItem(filme);
        biblioteca.emprestar(carlos, filme, retirada);

        System.out.println("\n=== ACERVO ===");
        biblioteca.listarAcervo();
    }
}
