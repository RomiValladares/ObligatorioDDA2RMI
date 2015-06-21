/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iu.poker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Jugador;
import logica.PartidaJuegoCasino;

/**
 *
 * @author Romi
 */
public class PanelFinPartida extends javax.swing.JPanel {

    /**
     * Creates new form PanelFinPartida
     */
    public PanelFinPartida() {
        initComponents();
    }

    public PanelFinPartida(final FramePoker parent, PartidaJuegoCasino partida, final Jugador ganador, final Jugador jugadorLogueado) {
        this();
        try {
            lblFinDePartida.setText("Fin de la partida " + partida.getNumeroPartida());
        } catch (RemoteException ex) {
            Logger.getLogger(PanelFinPartida.class.getName()).log(Level.SEVERE, null, ex);
        }
        lblGanador.setText("Ganador " + ganador);
        btnCerrarDialog.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                parent.cerrar();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFinDePartida = new javax.swing.JLabel();
        lblGanador = new javax.swing.JLabel();
        btnCerrarDialog = new javax.swing.JButton();

        setOpaque(false);

        lblFinDePartida.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFinDePartida.setText("Fin de la partida x.");

        lblGanador.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblGanador.setText("Ganador: Jugador algo");

        btnCerrarDialog.setText("OK");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCerrarDialog)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblGanador)
                        .addComponent(lblFinDePartida)))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(lblFinDePartida)
                .addGap(9, 9, 9)
                .addComponent(lblGanador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrarDialog)
                .addGap(41, 41, 41))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarDialog;
    private javax.swing.JLabel lblFinDePartida;
    private javax.swing.JLabel lblGanador;
    // End of variables declaration//GEN-END:variables
}
