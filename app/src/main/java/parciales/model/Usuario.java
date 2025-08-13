package parciales.model;

import java.util.Objects;
import java.util.Stack;
import java.util.UUID;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Clase que define al usuario, que posee un saldo, un portafolio de criptomonedas y un historial de transacciones (válidas e inválidas)
public class Usuario {

    public static final Logger logger = LogManager.getLogger(Usuario.class);
    private UUID id;
    private String nombre;
    private double saldoCop;
    private Bag<Criptomoneda> portafolio; // Se elige una bolsa porque simplemente interesa guardar las criptomonedas
                                          // sabiendo su cantidad
    private Stack<Transaccion> historial; // Se elige un stack porque cuando le metemos una transacción nos la guarda en
                                          // forma historial (LIFO)

    public Usuario(String nombre, double saldoCop) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del usuario no puede ser nulo o vacío");
            }
            if (saldoCop < 0) {
                throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
            }
            
            this.id = UUID.randomUUID();
            this.nombre = nombre;
            this.saldoCop = saldoCop;
            this.portafolio = new HashBag<>();
            this.historial = new Stack<>();

            logger.info("Usuario creado: {} con saldo inicial de ${} COP", nombre, saldoCop);
        } catch (Exception e) {
            logger.error("Error al crear usuario: {}", e.getMessage());
            throw e;
        }
    }

    public String getName() {
        return this.nombre;
    }

    public double getSaldoCOP() {
        return this.saldoCop;
    }

    public double getSaldoUSD() {
        return this.saldoCop / 4000;
    }

    public void disminuirSaldo(double n) {
        try {
            if (n < 0) {
                throw new IllegalArgumentException("La cantidad a disminuir no puede ser negativa");
            }
            if (this.saldoCop < n) {
                throw new IllegalArgumentException("Saldo insuficiente para la operación");
            }
            
            this.saldoCop -= n;
            logger.info("Saldo disminuido en ${} COP para usuario {}. Nuevo saldo: ${} COP", n, this.nombre, this.saldoCop);
        } catch (Exception e) {
            logger.error("Error al disminuir saldo para usuario {}: {}", this.nombre, e.getMessage());
            throw e;
        }
    }

    public void aumentarSaldo(double n) {
        try {
            if (n < 0) {
                throw new IllegalArgumentException("La cantidad a aumentar no puede ser negativa");
            }
            
            this.saldoCop += n;
            logger.info("Saldo aumentado en ${} COP para usuario {}. Nuevo saldo: ${} COP", n, this.nombre, this.saldoCop);
        } catch (Exception e) {
            logger.error("Error al aumentar saldo para usuario {}: {}", this.nombre, e.getMessage());
            throw e;
        }
    }

    public Bag<Criptomoneda> getPortafolio() {
        return this.portafolio;
    }

    public Bag<PortafolioReporte> getPortafolioReporte() {
        try {
            Bag<PortafolioReporte> portafolioReporte = new HashBag<>();

            for (Criptomoneda cripto : this.portafolio.uniqueSet()) {
                String nombreCriptomoneda = cripto.getName();
                int cantidadCriptomoneda = this.portafolio.getCount(cripto);
                
                PortafolioReporte criptosReporte = new PortafolioReporte(nombreCriptomoneda, cantidadCriptomoneda);
                portafolioReporte.add(criptosReporte);
            }

            logger.info("Generado reporte de portafolio para usuario {}: {} tipos de criptomonedas", this.nombre, portafolioReporte.uniqueSet().size());
            return portafolioReporte;
        } catch (Exception e) {
            logger.error("Error al generar reporte de portafolio para usuario {}: {}", this.nombre, e.getMessage());
            throw new RuntimeException("Ocurrió un error al generar el reporte de portafolio: " + e.getMessage());
        }
    }

    public double getValorPortafolioUSD() {
        try {
            double valor = 0;
            for (Criptomoneda cripto : portafolio) {
                valor += Double.parseDouble(cripto.getPrice_usd()) * portafolio.getCount(cripto);
            }
            logger.info("Valor del portafolio calculado para usuario {}: ${} USD", this.nombre, valor);
            return valor;
        } catch (NumberFormatException e) {
            logger.error("Error al convertir precio de criptomoneda para usuario {}: {}", this.nombre, e.getMessage());
            throw new RuntimeException("Ocurrió un error al calcular el valor del portafolio: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al calcular valor del portafolio para usuario {}: {}", this.nombre, e.getMessage());
            throw new RuntimeException("Ocurrió un error inesperado al calcular el valor del portafolio: " + e.getMessage());
        }
    }

    public Stack<Transaccion> getHistorial() {
        return this.historial;
    }

    public Stack<TransaccionReporte> getHistorialReporte() {
        try {
            Stack<TransaccionReporte> historialReporte = new Stack<>();

            for (Transaccion transaccion : this.historial) {
                try {
                    double precioUSD = Double.parseDouble(transaccion.getCriptomoneda().getPrice_usd());
                    double valorTransaccionCOP = precioUSD * transaccion.getCantidadCripto() * 4000;

                    TransaccionReporte transaccionReporte = new TransaccionReporte(transaccion.getEstatus(),
                            transaccion.getTipoTransaccion(), transaccion.getCriptomoneda().getName(),
                            transaccion.getCriptomoneda().getSymbol(), transaccion.getCantidadCripto(), precioUSD,
                            valorTransaccionCOP);

                    historialReporte.push(transaccionReporte);
                } catch (NumberFormatException e) {
                    logger.error("Error al convertir precio en transacción para usuario {}: {}", this.nombre, e.getMessage());
                }
            }

            logger.info("Generado historial de reportes para usuario {}: {} transacciones", this.nombre, historialReporte.size());
            return historialReporte;
        } catch (Exception e) {
            logger.error("Error al generar historial de reportes para usuario {}: {}", this.nombre, e.getMessage());
            throw new RuntimeException("Ocurrió un error al generar el historial de reportes: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name='" + nombre + '\'' +
                ", saldo=" + saldoCop +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Usuario))
            return false;
        Usuario usuario = (Usuario) obj;
        if (!id.equals(usuario.id))
            return false;
        if (!nombre.equals(usuario.nombre))
            return false;
        return saldoCop == usuario.saldoCop;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, saldoCop);
    }

}
