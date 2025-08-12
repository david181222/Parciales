package parciales.services;

import parciales.model.Criptomoneda;
import parciales.model.Usuario;

public class Transaccion {
    private String tipoTransaccion;
    private Criptomoneda criptomoneda;
    private int cantidadCriptomoneda;
    private Usuario usuario;

    public Transaccion(Usuario usuario){
        this.usuario = usuario;
    }

    public String getTipoTransaccion(){
        return this.tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion){
        this.tipoTransaccion = tipoTransaccion;
    }

    public Criptomoneda getCriptomoneda(){
        return this.criptomoneda;
    }

    public void setCriptomoneda(Criptomoneda userCriptomoneda){
        this.criptomoneda = userCriptomoneda;
    }

    public int getCantidadCripto(){
        return this.cantidadCriptomoneda;
    }

    public void setCantidadCripto(int userCantidadCripto){
        this.cantidadCriptomoneda = userCantidadCripto;
    }

    public Usuario getUsuario(){
        return this.usuario;
    }



}
