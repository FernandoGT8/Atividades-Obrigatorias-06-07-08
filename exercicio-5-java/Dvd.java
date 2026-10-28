// Categoria criada para testar a extensão: não exigia alterar nenhuma classe existente.
public class Dvd extends ItemBiblioteca {

    public Dvd(String codigo, String titulo) {
        super(codigo, titulo);
    }

    @Override
    public String tipo() {
        return "DVD";
    }

    @Override
    public int prazo() {
        return 3;
    }

    @Override
    public double multaPorDia() {
        return 2.00;
    }
}
