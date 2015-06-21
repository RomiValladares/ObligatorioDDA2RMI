/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssusuarios;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Romi
 */
public class ServiciosUsuarioV1 implements ServiciosUsuario {

    private HashMap<String, UsuarioV1> usuariosRegistrados = new HashMap<>();

    @Override
    public HashMap<String, UsuarioV1> getUsuarios() {
        try {
            JugadorV1 j1 = new JugadorV1("j1");
            j1.setContrasena("j1");
            j1.setSaldo(110);
            usuariosRegistrados.put(j1.getNombre(), j1);

            JugadorV1 j2 = new JugadorV1("j2");
            j2.setContrasena("j2");
            j2.setSaldo(90);
            usuariosRegistrados.put(j2.getNombre(), j2);

            JugadorV1 j3 = new JugadorV1("j3");
            j3.setContrasena("j3");
            j3.setSaldo(90);
            usuariosRegistrados.put(j3.getNombre(), j3);

            JugadorV1 j4 = new JugadorV1("j4");
            j4.setContrasena("j4");
            j4.setSaldo(90);
            usuariosRegistrados.put(j4.getNombre(), j4);

            JugadorV1 j5 = new JugadorV1("j5");
            j5.setContrasena("j5");
            j5.setSaldo(120);
            usuariosRegistrados.put(j5.getNombre(), j5);

            JugadorV1 j6 = new JugadorV1("j6");
            j6.setContrasena("j6");
            j6.setSaldo(180);
            usuariosRegistrados.put(j6.getNombre(), j6);

            JugadorV1 j7 = new JugadorV1("j7");
            j7.setContrasena("j7");
            j7.setSaldo(180);
            usuariosRegistrados.put(j7.getNombre(), j7);

            JugadorV1 jSaldoInsuficiente = new JugadorV1("jSaldoInsuficiente");
            jSaldoInsuficiente.setContrasena("jSaldoInsuficiente");
            jSaldoInsuficiente.setSaldo(45);
            usuariosRegistrados.put(jSaldoInsuficiente.getNombre(),
                    jSaldoInsuficiente);

        } catch (RemoteException ex) {
            Logger.getLogger(ServiciosUsuarioV1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuariosRegistrados;
    }

}
