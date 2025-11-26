package inventario.interfaces;
import java.util.List;
import java.util.Optional;

//PRINCIPIO DIP (Dependency Inversion Principle):
//Esta es la abstracción clave que permite desacoplar la lógica de negocio del almacenamiento de datos.

public interface IRepositorioInventario {
    void agregar(IProducto producto);
    Optional<IProducto> buscarPorId(String id);
    List<IProducto> obtenerTodos();
    List<IProducto> obtenerPorTipo(String tipo);
}