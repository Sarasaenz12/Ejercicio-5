package inventario.productos;

import inventario.interfaces.IProducto;
import java.util.logging.Logger;

abstract class ProductoBase implements IProducto {
    // Cada clase debe tener su logger si va a imprimir cosas
    protected static final Logger logger = Logger.getLogger(ProductoBase.class.getName());

    protected String identificador;
    protected String nombre;
    protected double precio;
    protected int stock;

    // CORRECCIÓN: Constructor protected (no public)
    protected ProductoBase(String identificador, String nombre, double precio, int stock) {
        if (precio < 0) throw new IllegalArgumentException("El precio no puede ser negativo");
        if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo");

        this.identificador = identificador;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // ... (Getters igual que antes) ...
    @Override
    public String obtenerIdentificador() { return identificador; }
    @Override
    public String obtenerNombre() { return nombre; }
    @Override
    public double obtenerPrecio() { return precio; }
    @Override
    public int obtenerCantidadStock() { return stock; }

    @Override
    public void actualizarStock(int cantidad) {
        int nuevoStock = this.stock + cantidad;
        if (nuevoStock < 0) throw new IllegalStateException("Stock insuficiente");
        this.stock = nuevoStock;
    }

    @Override
    public void mostrarInformacion() {
        // Usamos String.format para construir el mensaje limpio
        String info = String.format("ID: %s | %s | Stock: %d", identificador, nombre, stock);
        logger.info(info);
    }
}