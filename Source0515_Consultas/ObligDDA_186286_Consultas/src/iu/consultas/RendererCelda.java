/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu.consultas;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import logica.ssjuegos.JuegoCasino;

/**
 *
 * @author Romi
 */
public class RendererCelda extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Object item = value;

        String string = getString(item);

        return super.getListCellRendererComponent(list, string, index, isSelected, cellHasFocus);
    }

    private String getString(Object obj) {
        String invoke = "";

        if (obj instanceof JuegoCasino) {
            try {
                invoke = ((JuegoCasino) obj).getNombre();
            } catch (RemoteException ex) {
                Logger.getLogger(RendererCelda.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return invoke;
    }
}
