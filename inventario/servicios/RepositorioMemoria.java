package inventario.servicios;

import inventario.interfaces.IProducto;
import inventario.interfaces.IRepositorioInventario;
import java.util.*;

public class RepositorioMemoria implements IRepositorioInventario {
    private final Map<String, IProducto> baseDeDatos = new HashMap<>();

    @Override
    public void agregar(IProducto producto) {
        if (producto == null) throw new IllegalArgumentException("Nulo");
        baseDeDatos.put(producto.obtenerIdentificador(), producto);
    }

    @Override
    public Optional<IProducto> buscarPorId(String id) {
        return Optional.ofNullable(baseDeDatos.get(id));
    }

    @Override
    public List<IProducto> obtenerTodos() {
        // CORRECCIÓN: Usamos List.copyOf para devolver una lista inmutable de los valores
        return List.copyOf(baseDeDatos.values());
    }

    @Override
    public List<IProducto> obtenerPorTipo(String tipo) {
        // CORRECCIÓN: Sintaxis moderna de Java 16+
        return baseDeDatos.values().stream()
                .filter(p -> p.obtenerTipo().equalsIgnoreCase(tipo))
                .toList(); // Mucho más limpio que .collect(Collectors.toList())
    }
}