package inventario.productos;

import inventario.interfaces.IDescargable;
import inventario.servicios.Configuracion;
import java.util.UUID;

//PRINCIPIO SRP (Single Responsibility Principle):
//Cumple SRP porque gestiona únicamente la lógica específica de su tipo (tamaño/descarga),

//PRINCIPIO ISP (Interface Segregation Principle):
//Implementa IDescargable pero NO se le obliga a implementar métodos de peso o envío.

//PRINCIPIO LSP (Liskov Substitution Principle):
//Respeta el "contrato" de la clase base ProductoBase, asegurando que métodos

public class ProductoDigital extends ProductoBase implements IDescargable {
    private double tamanoMB;

    public ProductoDigital(String id, String nombre, double precio, int stock, double tamanoMB) {
        super(id, nombre, precio, stock);
        this.tamanoMB = tamanoMB;
    }

    @Override
    public String obtenerTipo() { return "DIGITAL"; }

    @Override
    public String generarEnlaceDescarga() {
        // Generación dinámica de token
        return Configuracion.URL_BASE_DESCARGA + identificador + "?token=" + UUID.randomUUID();
    }

    @Override
    public double obtenerTamanoArchivo() { return tamanoMB; }

    @Override
    public boolean verificarLicencia(String usuarioId) {
        // Simulación: en vida real consultaría una BD de licencias
        return usuarioId != null && !usuarioId.isEmpty();
    }
}