package parciales.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.UUID;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

import parciales.services.Transaccion;

public class Usuario {
    private UUID id;
    private String nombre;
    private double saldoCop;
    private Bag<Criptomoneda> portafolio;
    private Stack<Transaccion> historial;

    public Usuario(String nombre, double saldoCop) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.saldoCop = saldoCop;
        this.portafolio = new HashBag<>();
        this.historial = new Stack<>();

    }

    public String getName() {
        return this.nombre;
    }

    public double getSaldo() {
        return this.saldoCop;
    }

    public void disminuirSaldo(double n){
        this.saldoCop -= n;
    }

    public void aumentarSaldo(double n){
        this.saldoCop -= n;
    }

    public Bag<Criptomoneda> getPortafolio() {
        return this.portafolio;
    }



    public Stack<Transaccion> getHistorial() {
        return this.historial;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name='" + nombre + '\'' +
                ", saldo=" + saldoCop +
                '}';
    }

}
