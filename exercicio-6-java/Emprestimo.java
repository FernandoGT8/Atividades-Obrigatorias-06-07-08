import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo {
    private final ItemBiblioteca item;
    private final Usuario usuario;
    private final LocalDate dataRetirada;
    private final LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucao;
    private double valorMulta;

    public Emprestimo(ItemBiblioteca item, Usuario usuario, LocalDate dataRetirada) {
        this.item = item;
        this.usuario = usuario;
        this.dataRetirada = dataRetirada;
        this.dataDevolucaoPrevista = dataRetirada.plusDays(item.prazo());
    }

    public boolean isAberto() {
        return dataDevolucao == null;
    }

    public double registrarDevolucao(LocalDate data) {
        long diasAtraso = ChronoUnit.DAYS.between(dataDevolucaoPrevista, data);
        dataDevolucao = data;
        valorMulta = diasAtraso > 0 ? diasAtraso * item.multaPorDia() : 0;
        return valorMulta;
    }

    public ItemBiblioteca getItem() {
        return item;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public double getValorMulta() {
        return valorMulta;
    }
}
