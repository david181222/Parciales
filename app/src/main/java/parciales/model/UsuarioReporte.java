package parciales.model;

import java.util.Objects;
import java.util.Stack;

import org.apache.commons.collections4.Bag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//El objetivo de esta clase es filtrar la información del usuario para imprimir los diferentes usuarios en el JSON según los requerimientos
// dado que GSON para imprimir en formato JSON como tal debe mapear objetos, se crearon las clases de tipo -Reporte para facilitar este proceso
public class UsuarioReporte {

    public static final Logger logger = LogManager.getLogger(UsuarioReporte.class);
    private String nombreReporte;
    private double saldoUSDReporte;
    private double valorPortafolioReporte;
    private Bag<PortafolioReporte> portafolioReporte;
    private Stack<TransaccionReporte> historialReporte;

    public UsuarioReporte(String nombre, double saldoUSD, double valorPortafolio, Bag<PortafolioReporte> portafolio, Stack<TransaccionReporte> historial) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del reporte no puede ser nulo o vacío");
            }
            if (saldoUSD < 0) {
                throw new IllegalArgumentException("El saldo USD no puede ser negativo");
            }
            if (valorPortafolio < 0) {
                throw new IllegalArgumentException("El valor del portafolio no puede ser negativo");
            }
            if (portafolio == null) {
                throw new IllegalArgumentException("El portafolio de reporte no puede ser nulo");
            }
            if (historial == null) {
                throw new IllegalArgumentException("El historial de reporte no puede ser nulo");
            }
            
            this.nombreReporte = nombre;
            this.saldoUSDReporte = saldoUSD;
            this.valorPortafolioReporte = valorPortafolio;
            this.portafolioReporte = portafolio;
            this.historialReporte = historial;
            
            logger.info("Reporte de usuario creado: {} - Saldo: ${} USD - Portafolio: ${} USD - Transacciones: {}", 
                nombre, saldoUSD, valorPortafolio, historial.size());
        } catch (Exception e) {
            logger.error("Error al crear reporte de usuario: {}", e.getMessage());
            throw e;
        }
    }


    public String getNombreReporte() {
        return this.nombreReporte;
    }

    public double getSaldoUSDReporte() {
        return this.saldoUSDReporte;
    }

    public double getValorPortafolioReporte() {
        return this.valorPortafolioReporte;
    }

    public Bag<PortafolioReporte> getPortafolioReporte() {
        return this.portafolioReporte;
    }

    public Stack<TransaccionReporte> getHistorialReporte() {
        return this.historialReporte;
    }

    @Override
    public String toString() {
        return "UsuarioReporte{" +
                "nombreReporte='" + nombreReporte + '\'' +
                ", saldoUSDReporte=" + saldoUSDReporte +
                ", valorPortafolioReporte=" + valorPortafolioReporte +
                ", portafolioReporte='" + portafolioReporte + '\'' +
                ", historialReporte=" + historialReporte +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof UsuarioReporte))
            return false;
        UsuarioReporte usuario = (UsuarioReporte) obj;
        if (!nombreReporte.equals(usuario.nombreReporte))
            return false;
        if (saldoUSDReporte != usuario.saldoUSDReporte)
            return false;
        if (valorPortafolioReporte != usuario.valorPortafolioReporte)
            return false;
        if (!portafolioReporte.equals(usuario.portafolioReporte))
            return false;
        return historialReporte.equals(usuario.historialReporte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreReporte, saldoUSDReporte, valorPortafolioReporte, portafolioReporte, historialReporte);
    }

}
