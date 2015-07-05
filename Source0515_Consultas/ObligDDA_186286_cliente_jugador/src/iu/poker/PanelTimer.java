/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu.poker;

import java.awt.Color;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Romi
 */
public class PanelTimer extends javax.swing.JPanel implements Runnable {

    private int hasta;
    private volatile int segundoActual;
    private volatile boolean on;
    private Timer tareaTimer = new Timer();

    /**
     * Creates new form PanelTimer
     */
    public PanelTimer() {
        initComponents();
    }

    public PanelTimer(int segundosHasta) {
        this();
        this.hasta = segundosHasta;
        this.segundoActual = segundosHasta;
    }

    public PanelTimer(TareaTimer t, int segundosHasta) {
        this(segundosHasta);
        //this.tareaTimer = t;
    }

    public void cancelar() {
        tareaTimer.cancel();
        debug("cancelar");
    }

    public void resetear() {
        debug("resetear");
        cancelar();
        setSegundoActual(hasta);
        comenzar();
    }

    protected void comenzar() {
        tareaTimer = new Timer();
        tareaTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (segundoActual > 0) {
                    setSegundoActual(--segundoActual);
                } else {
                    tareaTimer.cancel();
                }
            }
        }, 0, 1000);
    }

    @Override
    public void run() {
        on = true;
        while (on && segundoActual > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PanelTimer.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (on) {
                setSegundoActual(--segundoActual);
            }
            debug(null);
        }
        if (segundoActual == 0) {
            finalizoTimer();
        }
    }

    private void setFont() {
        if (segundoActual == 0) {
            lblQuedanOTimeout.setText("TIMEOUT");
            lblQuedanOTimeout.setForeground(Color.red);
        } else if (segundoActual <= 3) {
            lblQuedanOTimeout.setText("quedan");
            lblSegundos.setForeground(Color.red);
        } else {
            lblQuedanOTimeout.setText("quedan");
            lblSegundos.setForeground(Color.black);
        }
        lblSegundos.setText("" + segundoActual);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSegundos = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblQuedanOTimeout = new javax.swing.JLabel();

        lblSegundos.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblSegundos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSegundos.setText("10");

        jLabel2.setText("seg.");

        lblQuedanOTimeout.setText("quedan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblQuedanOTimeout)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSegundos, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(lblQuedanOTimeout))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblQuedanOTimeout;
    private javax.swing.JLabel lblSegundos;
    // End of variables declaration//GEN-END:variables

    private void setSegundoActual(int i) {
        segundoActual = i;
        setFont();
    }

    private void finalizoTimer() {
        if (tareaTimer != null) {
            //tareaTimer.finalizoTimer();
        }
    }

    void deshabilitarTimer() {
        cancelar();
//        lblSegundos.setText("-");
    }

    void setTimeout(int timeout) {
        this.hasta = timeout;
    }

    private void debug(String msj) {
        System.out.println("DEBUG PANEL TIMER date=" + new Date() + " segundo=" + segundoActual + " " + msj);
    }

    public interface TareaTimer {

        public void finalizoTimer();
    }
}
