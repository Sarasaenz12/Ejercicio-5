package inventario.productos;

import inventario.interfaces.IEnviable;
import inventario.servicios.Configuracion; // Usamos config centralizada

//PRINCIPIO SRP (Single Responsibility Principle):
// Cumple SRP porque gestiona únicamente la lógica específica de su tipo (peso/envío),

//PRINCIPIO ISP (Interface Segregation Principle):
// Implementa IEnviable pero NO se le obliga a implementar métodos de descarga.

//PRINCIPIO LSP (Liskov Substitution Principle):
//Respeta el "contrato" de la clase base ProductoBase, asegurando que métodos

public class ProductoFisico extends ProductoBase implements IEnviable {
    private double pesoKg;

    public ProductoFisico(String id, String nombre, double precio, int stock, double pesoKg) {
        super(id, nombre, precio, stock);
        this.pesoKg = pesoKg;
    }

    @Override
    public String obtenerTipo() { return "FÍSICO"; }

    @Override
    public double calcularCostoEnvio(String destino) {
        // Lógica de negocio encapsulada aquí (SRP)
        // Usamos la constante de configuración en lugar de un número mágico
        double costo = pesoKg * Configuracion.COSTO_BASE_KM;
        if (destino.toLowerCase().contains("internacional")) {
            costo *= 1.5; // Recargo
        }
        return costo;
    }

    @Override
    public double obtenerPeso() { return pesoKg; }

    @Override
    public String prepararParaEnvio(String direccion) {
        return "Empaquetando " + nombre + " (" + pesoKg + "kg) para enviar a: " + direccion;
    }
}