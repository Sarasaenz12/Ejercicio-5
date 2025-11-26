package inventario.servicios;

import inventario.interfaces.IProducto;
import inventario.interfaces.IRepositorioInventario;
import inventario.interfaces.IEnviable;
import inventario.interfaces.IDescargable;
import java.util.logging.Logger;
import java.util.logging.Level;

public class GestorInventario {
    // Logger constante
    private static final Logger logger = Logger.getLogger(GestorInventario.class.getName());
    private final IRepositorioInventario repositorio;

    public GestorInventario(IRepositorioInventario repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarProducto(IProducto producto) {
        try {
            repositorio.agregar(producto);
            // Uso de logger en lugar de System.out
            logger.log(Level.INFO, "✅ Producto registrado: {0}", producto.obtenerNombre());
        } catch (Exception e) {
            // Uso de logger en lugar de System.err
            logger.log(Level.SEVERE, "❌ Error: {0}", e.getMessage());
        }
    }

    public void procesarVenta(String id, int cantidad) {
        repositorio.buscarPorId(id).ifPresentOrElse(prod -> {
            try {
                prod.actualizarStock(-cantidad);
                logger.log(Level.INFO, "💰 Venta procesada de: {0}", prod.obtenerNombre());
                gestionarEntrega(prod);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "❌ Error en venta: {0}", e.getMessage());
            }
        }, () -> logger.warning("Producto no encontrado"));
    }

    // CORRECCIÓN DE PATTERN MATCHING (java:S6201)
    private void gestionarEntrega(IProducto prod) {
        // Mira cómo declaramos 'enviable' directamente dentro del if
        if (prod instanceof IEnviable enviable) {
            String mensaje = enviable.prepararParaEnvio("Dirección del Cliente");
            logger.info(mensaje);
        } else if (prod instanceof IDescargable descargable) {
            // Ya no necesitamos hacer el cast manual ((IDescargable) prod)
            String link = descargable.generarEnlaceDescarga();
            logger.log(Level.INFO, "📧 Generando descarga... (Link simulado: {0})", link);
        }
    }

    public void imprimirReporte() {
        logger.info("\n--- INVENTARIO ---");
        repositorio.obtenerTodos().forEach(IProducto::mostrarInformacion);
    }
}