package parciales.services;

import java.util.LinkedList;
import java.util.Queue;

import parciales.model.Criptomoneda;
import parciales.model.Usuario;

//Esta clase tiene por objetivo desencolar las transacciones hechas en el main, de modo que podemos validarlas y procesar las operaciones en los usuarios
public class TransactionProcessorService {

    private static Queue<Transaccion> transacciones = new LinkedList<>(); // Se elige una cola para procesarlas en orden
                                                                          // de llegada (FIFO)

    public static void meterTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }

    public static Transaccion sacarTransaccion() {
        return transacciones.poll();
    }

    public static boolean transaccionesEnCola() {
        return !transacciones.isEmpty();
    }

    public static Transaccion procesarSiguienteTransaccion() {
        Transaccion transaccion = sacarTransaccion();
        return transaccion;
    }

    public static boolean ejecutarTransaccion(Transaccion transaccion) {
        Usuario usuario = transaccion.getUsuario();
        Criptomoneda cripto = transaccion.getCriptomoneda();
        int cantidad = transaccion.getCantidadCripto();
        String tipo = transaccion.getTipoTransaccion();

        try {
            if (tipo.equals("Compra")) {
                return ejecutarCompra(usuario, cripto, cantidad, transaccion);
            } else if (tipo.equals("Venta")) {
                return ejecutarVenta(usuario, cripto, cantidad, transaccion);
            }
            return false;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al ejecutar la transacción: " + e.getMessage());

        }
    }

    private static boolean ejecutarCompra(Usuario usuario, Criptomoneda cripto, int cantidad, Transaccion transaccion) {
        int fluctuacion = (int) (Math.random() * 20) - 10; // Entre el -10% y el 10%
        double precioFluctuado = Double.parseDouble(cripto.getPrice_usd()) * (1 + fluctuacion / 100.0);
        double precioTotalEnCOP = (precioFluctuado * cantidad) * 4000;

        // Validar si el usuario tiene dinero para la transaccion
        double saldoUsuarioCOP = usuario.getSaldoCOP();

        if (saldoUsuarioCOP >= precioTotalEnCOP) {
            usuario.disminuirSaldo(precioTotalEnCOP);

            for (int i = 0; i < cantidad; i++) {
                usuario.getPortafolio().add(cripto);
            }

            usuario.getHistorial().push(transaccion);

            System.out.println("Se completo la compra de " + transaccion.getCriptomoneda().getSymbol() + " en cantidad: "
                    + transaccion.getCantidadCripto() + " por el usuario: " + usuario.getName());
            return true;
        } else {
            throw new IllegalArgumentException("No se pudo completar la compra.");
        }
    }

    private static boolean ejecutarVenta(Usuario usuario, Criptomoneda cripto, int cantidad, Transaccion transaccion) {
        int cantidadCriptosEnPortafolio = usuario.getPortafolio().getCount(cripto);

        // Validar si el usuario tiene suficientes criptomonedas en su portafolio
        if (cantidadCriptosEnPortafolio >= cantidad && cantidad > 0) {

            int fluctuacion = (int) (Math.random() * 20) - 10; // Entre -10% y +10%
            double precioFluctuado = Double.parseDouble(cripto.getPrice_usd()) * (1 + fluctuacion / 100.0);
            double precioTotalUSD = precioFluctuado * cantidad;

            double precioTotalCOP = precioTotalUSD * 4000;
            usuario.aumentarSaldo(precioTotalCOP);

            for (int i = 0; i < cantidad; i++) {
                usuario.getPortafolio().remove(cripto);
            }

            usuario.getHistorial().push(transaccion);

            System.out.println("Se completó la venta de " + transaccion.getCriptomoneda().getSymbol() + " en cantidad: "
                    + transaccion.getCantidadCripto() + " por el usuario: " + usuario.getName());
            return true;
        } else {
            throw new IllegalArgumentException("No se pudo completar la venta.");
        }

    }

}
