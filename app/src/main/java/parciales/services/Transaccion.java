package parciales.services;

import java.util.Objects;

import parciales.interfaces.ITransaccion;
import parciales.model.Criptomoneda;
import parciales.model.Usuario;

public class Transaccion implements ITransaccion {
    private String tipoTransaccion;
    private Criptomoneda criptomoneda;
    private int cantidadCriptomoneda;
    private Usuario usuario;

    public Transaccion(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipoTransaccion() {
        return this.tipoTransaccion;
    }

    @Override
    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public Criptomoneda getCriptomoneda() {
        return this.criptomoneda;
    }

    @Override
    public void setCriptomoneda(Criptomoneda userCriptomoneda) {
        this.criptomoneda = userCriptomoneda;
    }

    public int getCantidadCripto() {
        return this.cantidadCriptomoneda;
    }

    @Override
    public void setCantidadCripto(int userCantidadCripto) {
        this.cantidadCriptomoneda = userCantidadCripto;
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
