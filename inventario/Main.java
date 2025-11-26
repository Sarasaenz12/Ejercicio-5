package inventario;

import inventario.interfaces.IRepositorioInventario;
import inventario.productos.ProductoDigital;
import inventario.productos.ProductoFisico;
import inventario.servicios.GestorInventario;
import inventario.servicios.RepositorioMemoria;
// 1. Importamos las clases de Logging
import java.util.logging.Logger;

public class Main {
    // 2. Definimos una constante para el Logger (es buena práctica que sea static final)
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        IRepositorioInventario repo = new RepositorioMemoria();
        GestorInventario gestor = new GestorInventario(repo);

        // 3. Reemplazamos System.out.println por logger.info()
        logger.info("Sistema de Inventario Iniciado...\n");

        // 2. Creación de productos
        ProductoFisico laptop = new ProductoFisico("F01", "Laptop Gamer", 1500.0, 10, 2.5);
        ProductoDigital ebook = new ProductoDigital("D01", "Libro Java Clean Code", 30.0, 100, 5.0);

        // 3. Registro
        gestor.registrarProducto(laptop);
        gestor.registrarProducto(ebook);

        // 4. Operaciones
        // Reemplazamos el otro System.out.println
        logger.info("\n--- Procesando Ventas ---");

        gestor.procesarVenta("F01", 1);
        gestor.procesarVenta("D01", 1);

        // 5. Reporte final
        gestor.imprimirReporte();
    }
}