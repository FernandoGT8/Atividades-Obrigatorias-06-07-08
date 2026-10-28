public class Revista extends ItemBiblioteca {

    public Revista(String codigo, String titulo) {
        super(codigo, titulo);
    }

    @Override
    public String tipo() {
        return "Revista";
    }

    @Override
    public int prazo() {
        return 7;
    }

    @Override
    public double multaPorDia() {
        return 1.00;
    }
}
