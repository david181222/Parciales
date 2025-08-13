package parciales.model;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import parciales.interfaces.ITransaccion;

//Clase que define una transacción (la que se operan en los turnos)
public class Transaccion implements ITransaccion {

    public static final Logger logger = LogManager.getLogger(Transaccion.class);
    private String estatus;
    private String tipoTransaccion;
    private Criptomoneda criptomoneda;
    private int cantidadCriptomoneda;
    private Usuario usuario;

    public Transaccion(Usuario usuario) {
        try {
            if (usuario == null) {
                throw new IllegalArgumentException("El usuario no puede ser nulo");
            }
            this.usuario = usuario;
            logger.info("Transacción creada para usuario: {}", usuario.getName());
        } catch (Exception e) {
            logger.error("Error al crear transacción: {}", e.getMessage());
            throw e;
        }
    }

    public String getTipoTransaccion() {
        return this.tipoTransaccion;
    }

    @Override
    public void setTipoTransaccion(String tipoTransaccion) {
        try {
            if (tipoTransaccion == null || tipoTransaccion.trim().isEmpty()) {
                throw new IllegalArgumentException("El tipo de transacción no puede ser nulo o vacío");
            }
            if (!tipoTransaccion.equals("Compra") && !tipoTransaccion.equals("Venta")) {
                throw new IllegalArgumentException("Tipo de transacción inválido. Solo se permiten 'Compra' o 'Venta'");
            }
            this.tipoTransaccion = tipoTransaccion;
            logger.info("Tipo de transacción establecido: {} para usuario {}", tipoTransaccion, usuario.getName());
        } catch (Exception e) {
            logger.error("Error al establecer tipo de transacción: {}", e.getMessage());
            throw e;
        }
    }

    public String getEstatus() {
        return this.estatus;
    }

    public void setEstatus(String estatus) {
        try {
            if (estatus == null || estatus.trim().isEmpty()) {
                throw new IllegalArgumentException("El estatus no puede ser nulo o vacío");
            }
            if (!estatus.equals("Valida") && !estatus.equals("Invalida")) {
                throw new IllegalArgumentException("Estatus inválido. Solo se permiten 'Valida' o 'Invalida'");
            }
            this.estatus = estatus;
            logger.info("Estatus establecido: {} para transacción de usuario {}", estatus, usuario.getName());
        } catch (Exception e) {
            logger.error("Error al establecer estatus de transacción: {}", e.getMessage());
            throw e;
        }
    }

    public Criptomoneda getCriptomoneda() {
        return this.criptomoneda;
    }

    @Override
    public void setCriptomoneda(Criptomoneda userCriptomoneda) {
        try {
            if (userCriptomoneda == null) {
                throw new IllegalArgumentException("La criptomoneda no puede ser nula");
            }
            this.criptomoneda = userCriptomoneda;
            logger.info("Criptomoneda establecida: {} para transacción de usuario {}", userCriptomoneda.getSymbol(), usuario.getName());
        } catch (Exception e) {
            logger.error("Error al establecer criptomoneda: {}", e.getMessage());
            throw e;
        }
    }

    public int getCantidadCripto() {
        return this.cantidadCriptomoneda;
    }

    @Override
    public void setCantidadCripto(int userCantidadCripto) {
        try {
            if (userCantidadCripto <= 0) {
                throw new IllegalArgumentException("La cantidad de criptomonedas debe ser mayor a cero");
            }
            this.cantidadCriptomoneda = userCantidadCripto;
            logger.info("Cantidad establecida: {} para transacción de usuario {}", userCantidadCripto, usuario.getName());
        } catch (Exception e) {
            logger.error("Error al establecer cantidad de criptomonedas: {}", e.getMessage());
            throw e;
        }
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "tipoTransaccion='" + tipoTransaccion + '\'' +
                ", criptomoneda=" + criptomoneda +
                ", cantidadCriptomoneda=" + cantidadCriptomoneda +
                ", usuario=" + usuario +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Transaccion))
            return false;
        Transaccion transaccion = (Transaccion) obj;
        if (!tipoTransaccion.equals(transaccion.tipoTransaccion))
            return false;
        if (!criptomoneda.equals(transaccion.criptomoneda))
            return false;
        if (cantidadCriptomoneda != transaccion.cantidadCriptomoneda)
            return false;
        return usuario.equals(transaccion.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoTransaccion, criptomoneda, cantidadCriptomoneda, usuario);
    }

}
