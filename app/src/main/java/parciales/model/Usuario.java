package parciales.model;

import java.util.Objects;
import java.util.Stack;
import java.util.UUID;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

import parciales.services.Transaccion;

public class Usuario {
    private UUID id;
    private String nombre;
    private double saldoCop;
    private Bag<Criptomoneda> portafolio; //Se elige una bolsa porque simplemente interesa guardar las criptomonedas sabiendo su cantidad
    private Stack<Transaccion> historial; //Se elige un stack porque cuando le metemos una transacción nos la guarda en forma historial (LIFO)

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

    public double getSaldoCOP() {
        return this.saldoCop;
    }
    public double getSaldoUSD() {
        return this.saldoCop / 4000;
    }

    public void disminuirSaldo(double n) {
        this.saldoCop -= n;
    }

    public void aumentarSaldo(double n) {
        this.saldoCop += n;
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
