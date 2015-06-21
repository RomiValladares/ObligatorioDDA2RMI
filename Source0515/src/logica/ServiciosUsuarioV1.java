/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Romi
 */
public class ServiciosUsuarioV1 implements ServiciosUsuario {

    private HashMap<String, Usuario> usuariosRegistrados = new HashMap<>();

    @Override
    public HashMap<String, Usuario> getUsuarios() {
        Jugador j1 = new Jugador("j1");
        j1.setContrasena("j1");
        j1.setSaldo(110);
        usuariosRegistrados.put(j1.getNombre(), j1);

        Jugador j2 = new Jugador("j2");
        j2.setContrasena("j2");
        j2.setSaldo(90);
        usuariosRegistrados.put(j2.getNombre(), j2);

        Jugador j3 = new Jugador("j3");
        j3.setContrasena("j3");
        j3.setSaldo(90);
        usuariosRegistrados.put(j3.getNombre(), j3);

        Jugador j4 = new Jugador("j4");
        j4.setContrasena("j4");
        j4.setSaldo(90);
        usuariosRegistrados.put(j4.getNombre(), j4);

        Jugador j5 = new Jugador("j5");
        j5.setContrasena("j5");
        j5.setSaldo(120);
        usuariosRegistrados.put(j5.getNombre(), j5);

        Jugador j6 = new Jugador("j6");
        j6.setContrasena("j6");
        j6.setSaldo(180);
        usuariosRegistrados.put(j6.getNombre(), j6);

        Jugador j7 = new Jugador("j7");
        j7.setContrasena("j7");
        j7.setSaldo(180);
        usuariosRegistrados.put(j7.getNombre(), j7);

        Jugador jSaldoInsuficiente = new Jugador("jSaldoInsuficiente");
        jSaldoInsuficiente.setContrasena("jSaldoInsuficiente");
        jSaldoInsuficiente.setSaldo(45);
        usuariosRegistrados.put(jSaldoInsuficiente.getNombre(),
                jSaldoInsuficiente);

        return usuariosRegistrados;
    }

}
