package inventario.interfaces;

//PRINCIPIO ISP (Interface Segregation Principle):
// Aquí está la clave de ISP. Se separaron las capacidades en interfaces pequeñas.
// * Solo para productos digitales.

public interface IProducto {
    String obtenerIdentificador();
    String obtenerNombre();
    double obtenerPrecio();
    int obtenerCantidadStock();
    void actualizarStock(int cantidad);
    String obtenerTipo();
    void mostrarInformacion();
}