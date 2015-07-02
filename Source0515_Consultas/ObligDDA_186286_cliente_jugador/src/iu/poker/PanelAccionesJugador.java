/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu.poker;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import logica.ssjuegos.poker.EventoManoPoker;
import logica.ssjuegos.poker.EventoManoPoker.EventosManoPoker;
import logica.ssjuegos.poker.EventoPartidaPoker;
import logica.ssjuegos.poker.EventoPartidaPoker.EventosPartidaPoker;
import logica.ssjuegos.poker.PartidaPoker;
import logica.ssjuegos.poker.figuras.FiguraPoker;
import logica.ssusuarios.Jugador;

/**
 *
 * @author Romi
 */
public class PanelAccionesJugador extends javax.swing.JPanel implements PanelTimer.TareaTimer {

    private int segundosHasta = 10;
    private FramePoker framePoker;
    private BufferedImage image;
    private boolean showTimer;

    public PanelAccionesJugador() {
        initComponents();
    }

    public PanelAccionesJugador(boolean showTimer) {
        this();
        setShowTimer(showTimer);
    }

    /**
     * Creates new form PanelAccionesJugador
     *
     * @param parent con la cual se va a comunicar
     */
    public PanelAccionesJugador(FramePoker parent) {
        this(false);
        this.framePoker = parent;

        try {
            image = ImageIO.read(new File("src/imgs/cuadropoker2.png"));
        } catch (IOException ex) {
            Logger.getLogger(PanelAccionesJugador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PanelAccionesJugador(FramePoker parent, boolean showTimer) {
        this(parent);
        setShowTimer(showTimer);
    }

    public void setShowTimer(boolean showTimer) {
        panelTimer.setVisible(showTimer);
        this.showTimer = showTimer;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelApuesta = new javax.swing.JPanel();
        btnPasar = new javax.swing.JToggleButton();
        btnApostar = new javax.swing.JButton();
        inputApuesta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblApuestaMaxima = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panelAceptarApuesta = new javax.swing.JPanel();
        lblNuevaApuesta = new javax.swing.JLabel();
        btnAceptarApuesta = new javax.swing.JButton();
        btnPasarApuesta = new javax.swing.JToggleButton();
        panelEsperando = new javax.swing.JPanel();
        lblEsperando = new javax.swing.JLabel();
        lblFigura = new javax.swing.JLabel();
        panelDialog = new javax.swing.JPanel();
        txtDialog = new javax.swing.JLabel();
        btnOKDialog = new javax.swing.JButton();
        btnNoDialog = new javax.swing.JButton();
        panelTimer = new iu.poker.PanelTimer(this, segundosHasta);

        setMaximumSize(new java.awt.Dimension(540, 175));
        setMinimumSize(new java.awt.Dimension(540, 175));
        setPreferredSize(new java.awt.Dimension(540, 175));
        setRequestFocusEnabled(false);

        panelApuesta.setMaximumSize(new java.awt.Dimension(200, 40));
        panelApuesta.setMinimumSize(new java.awt.Dimension(200, 40));
        panelApuesta.setOpaque(false);
        panelApuesta.setPreferredSize(new java.awt.Dimension(200, 40));

        btnPasar.setText("Pasar");
        btnPasar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasarActionPerformed(evt);
            }
        });

        btnApostar.setText("Apostar");
        btnApostar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApostarActionPerformed(evt);
            }
        });

        jLabel3.setText("Puede apostar (menor a ");

        lblApuestaMaxima.setText("$ x");

        jLabel5.setText(") o pasar.");

        javax.swing.GroupLayout panelApuestaLayout = new javax.swing.GroupLayout(panelApuesta);
        panelApuesta.setLayout(panelApuestaLayout);
        panelApuestaLayout.setHorizontalGroup(
            panelApuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelApuestaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblApuestaMaxima)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(inputApuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnApostar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPasar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelApuestaLayout.setVerticalGroup(
            panelApuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelApuestaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelApuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelApuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPasar)
                        .addComponent(btnApostar)
                        .addComponent(inputApuesta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelApuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(lblApuestaMaxima)
                        .addComponent(jLabel5)))
                .addContainerGap(6, Short.MAX_VALUE))
        );

        panelAceptarApuesta.setMaximumSize(new java.awt.Dimension(200, 40));
        panelAceptarApuesta.setMinimumSize(new java.awt.Dimension(200, 40));
        panelAceptarApuesta.setOpaque(false);
        panelAceptarApuesta.setPreferredSize(new java.awt.Dimension(200, 40));

        lblNuevaApuesta.setText("El jugador x aposto $x. Entrar en la apuesta?");

        btnAceptarApuesta.setText("Apostar");
        btnAceptarApuesta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarApuestaActionPerformed(evt);
            }
        });

        btnPasarApuesta.setText("Pasar");
        btnPasarApuesta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasarApuestaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAceptarApuestaLayout = new javax.swing.GroupLayout(panelAceptarApuesta);
        panelAceptarApuesta.setLayout(panelAceptarApuestaLayout);
        panelAceptarApuestaLayout.setHorizontalGroup(
            panelAceptarApuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAceptarApuestaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNuevaApuesta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addComponent(btnAceptarApuesta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPasarApuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelAceptarApuestaLayout.setVerticalGroup(
            panelAceptarApuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAceptarApuestaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelAceptarApuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPasarApuesta)
                    .addComponent(btnAceptarApuesta))
                .addContainerGap())
            .addGroup(panelAceptarApuestaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNuevaApuesta)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelEsperando.setBorder(new javax.swing.border.MatteBorder(null));
        panelEsperando.setMaximumSize(new java.awt.Dimension(200, 40));
        panelEsperando.setMinimumSize(new java.awt.Dimension(200, 40));
        panelEsperando.setOpaque(false);
        panelEsperando.setPreferredSize(new java.awt.Dimension(200, 40));

        lblEsperando.setText("Esperando...");

        lblFigura.setText("RealizoFiguraX");

        javax.swing.GroupLayout panelEsperandoLayout = new javax.swing.GroupLayout(panelEsperando);
        panelEsperando.setLayout(panelEsperandoLayout);
        panelEsperandoLayout.setHorizontalGroup(
            panelEsperandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEsperandoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFigura, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEsperando, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelEsperandoLayout.setVerticalGroup(
            panelEsperandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEsperandoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelEsperandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEsperando)
                    .addComponent(lblFigura))
                .addGap(117, 117, 117))
        );

        panelDialog.setBorder(new javax.swing.border.MatteBorder(null));
        panelDialog.setMaximumSize(new java.awt.Dimension(200, 40));
        panelDialog.setMinimumSize(new java.awt.Dimension(200, 40));
        panelDialog.setOpaque(false);
        panelDialog.setPreferredSize(new java.awt.Dimension(200, 40));

        txtDialog.setText("Texto whatever");

        btnOKDialog.setText("OK");

        btnNoDialog.setText("No");

        javax.swing.GroupLayout panelDialogLayout = new javax.swing.GroupLayout(panelDialog);
        panelDialog.setLayout(panelDialogLayout);
        panelDialogLayout.setHorizontalGroup(
            panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDialog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNoDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOKDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelDialogLayout.setVerticalGroup(
            panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDialog)
                    .addComponent(btnOKDialog)
                    .addComponent(btnNoDialog))
                .addGap(112, 112, 112))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(panelDialog, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                        .addComponent(panelEsperando, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                        .addComponent(panelApuesta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                        .addComponent(panelAceptarApuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelApuesta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAceptarApuesta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEsperando, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPasarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasarActionPerformed
        try {
            mostrarPanelEsperando("Esperando a que otro jugador apueste o todos pasen.");
            framePoker.pasar();
        } catch (Exception ex) {
            Logger.getLogger(FramePoker.class.getName()).log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnPasarActionPerformed

    private void btnApostarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApostarActionPerformed
        try {
            double apuesta = Double.parseDouble(inputApuesta.getText());
            if (apuesta <= 0) {
                throw new NumberFormatException();
            }
            framePoker.apostar(apuesta);
        } catch (NumberFormatException ex) {
            Logger.getLogger(FramePoker.class.getName()).log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(this, "Ingrese un numero valido.");
        }
    }//GEN-LAST:event_btnApostarActionPerformed

    private void btnAceptarApuestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarApuestaActionPerformed
        // TODO add your handling code here:
        framePoker.aceptarApuesta();
    }//GEN-LAST:event_btnAceptarApuestaActionPerformed

    private void btnPasarApuestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasarApuestaActionPerformed
        try {
            mostrarPanelEsperando(null, "Quedaste fuera de la mano.");
            framePoker.pasarApuesta();
        } catch (Exception ex) {
            Logger.getLogger(FramePoker.class.getName()).log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnPasarApuestaActionPerformed

    protected void mostrarPanelApuesta(double apuestaMaxima) {
        panelDialog.setVisible(false);
        panelApuesta.setVisible(true);
        lblApuestaMaxima.setText("$" + apuestaMaxima);
        panelAceptarApuesta.setVisible(false);
        panelEsperando.setVisible(false);
    }

    public void mostrarPanelEsperando(String nuevoMensaje) {
        setVisible(true);

        panelEsperando.setVisible(true);
        panelAceptarApuesta.setVisible(false);
        panelApuesta.setVisible(false);
        panelAceptarApuesta.setVisible(false);
        panelDialog.setVisible(false);

        lblFigura.setVisible(false);

        lblEsperando.setVisible(true);
        lblEsperando.setText(nuevoMensaje == null ? "Esperando..." : nuevoMensaje);
    }

    private void mostrarPanelEsperando(String primerMensaje, String segundoMensaje) {
        boolean mostrarDialog = panelDialog.isVisible();

        mostrarPanelEsperando(segundoMensaje);
        lblFigura.setVisible(true);
        lblFigura.setText(primerMensaje);

        if (mostrarDialog) {
            panelDialog.setVisible(true);
        }
    }

    protected void mostrarFiguraRealizada(FiguraPoker figuraRealizada) {

        String figura;
        if (figuraRealizada == null) {
            figura = "No realizo ninguna figura. ";
        } else {
            figura = "Realizo figura " + figuraRealizada + ". ";
        }
        mostrarPanelEsperando(figura, "Esperando a que los otros jugadores acepten la apuesta y descarten sus cartas");
    }

    private void mostrarPanelDialog() {
        panelApuesta.setVisible(false);

        if (framePoker.isModoDescartar()) {
            framePoker.setModoDescartarse(false);
            panelEsperando.setVisible(false);
        } else {
            panelEsperando.setVisible(true);
            lblFigura.setVisible(true);
        }

        lblEsperando.setVisible(false);

        panelAceptarApuesta.setVisible(false);
        panelDialog.setVisible(true);

        for (ActionListener al : btnOKDialog.getActionListeners()) {
            btnOKDialog.removeActionListener(al);
        }
        for (ActionListener al : btnOKDialog.getActionListeners()) {
            btnNoDialog.removeActionListener(al);
        }

        btnNoDialog.setVisible(false);
    }

    protected void mostrarPanelDialog(EventoPartidaPoker evento) {
        mostrarPanelDialog();
        txtDialog.setText(evento.getDescripcion());
    }

    protected void mostrarPanelDialog(EventoManoPoker evento, Map.Entry<Jugador, FiguraPoker> ganadorYFigura, final int totalJugadoresApuesta) {
        if (evento.getEvento().equals(EventosManoPoker.GANADOR)) {
            mostrarPanelDialog();

            String ganador = null;
            try {
                ganador = ganadorYFigura.getKey().getEtiqueta();
            } catch (RemoteException ex) {
                Logger.getLogger(PanelAccionesJugador.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (ganadorYFigura.getValue() != null) {
                txtDialog.setText(ganador
                        + " gana con figura " + ganadorYFigura.getValue());
            } else if (totalJugadoresApuesta == 1) {
                txtDialog.setText(ganador
                        + " gana como unico apostador.");
            } else {
                txtDialog.setText(ganador
                        + " gana sin figura, por carta mas alta.");
            }

            btnOKDialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (framePoker.puedeSeguirEnJuego()) {
                        mostrarPanelDialog(EventosManoPoker.FINALIZO_MANO, totalJugadoresApuesta != 1);
                    } else {
                        mostrarPanelDialog(EventosPartidaPoker.JUGADOR_SALDO_INSUFICIENTE);
                    }
                }
            });
        } else if ((evento.getEvento().equals(EventosManoPoker.GANADOR) || evento.getEvento().equals(EventosManoPoker.FINALIZO_MANO)) && ganadorYFigura == null) {
            mostrarPanelDialog();
            panelEsperando.setVisible(false);
            txtDialog.setText("Finalizo la mano sin ganador. Se acumula el pozo.");
            btnOKDialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (framePoker.puedeSeguirEnJuego()) {
                        mostrarPanelDialog(EventosManoPoker.FINALIZO_MANO, false);
                    } else {
                        mostrarPanelDialog(EventosPartidaPoker.JUGADOR_SALDO_INSUFICIENTE);
                    }
                }
            });
        }
    }

    private void mostrarPanelDialog(EventosManoPoker evento) {
        mostrarPanelDialog(evento, true);
    }

    private void mostrarPanelDialog(EventosManoPoker evento, boolean mostrarPanelEsperando) {
        if (evento.equals(EventosManoPoker.FINALIZO_MANO)) {
            mostrarPanelDialog();
            panelEsperando.setVisible(mostrarPanelEsperando);
            btnNoDialog.setVisible(true);
            txtDialog.setText("Finalizo la mano. Seguir en juego?");

            btnOKDialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarPanelEsperando("Esperando...");
                    framePoker.continuarEnJuego(true);
                }
            });

            btnNoDialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    framePoker.continuarEnJuego(false);
                }
            });
        }
    }

    private void mostrarPanelDialog(EventosPartidaPoker evento) {
        if (evento.equals(EventosPartidaPoker.JUGADOR_SALDO_INSUFICIENTE)) {

            mostrarPanelDialog();

            panelEsperando.setVisible(false);
            framePoker.checkPuedeJugar();
            txtDialog.setText("Saldo insuficiente para seguir en juego. Queda fuera de la partida.");

            btnOKDialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    framePoker.cerrar();
                }
            });
        }
    }

    public void cancelar() {
        System.out.println("cancelar");
        panelTimer.cancelar();
    }

    public void resetear() {
        System.out.println("resetear");
        panelTimer.resetear();
    }

    protected void comenzar() {
        System.out.println("comenzar");
        panelTimer.comenzar();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptarApuesta;
    private javax.swing.JButton btnApostar;
    private javax.swing.JButton btnNoDialog;
    private javax.swing.JButton btnOKDialog;
    private javax.swing.JToggleButton btnPasar;
    private javax.swing.JToggleButton btnPasarApuesta;
    private javax.swing.JTextField inputApuesta;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblApuestaMaxima;
    private javax.swing.JLabel lblEsperando;
    private javax.swing.JLabel lblFigura;
    private javax.swing.JLabel lblNuevaApuesta;
    private javax.swing.JPanel panelAceptarApuesta;
    private javax.swing.JPanel panelApuesta;
    private javax.swing.JPanel panelDialog;
    private javax.swing.JPanel panelEsperando;
    private iu.poker.PanelTimer panelTimer;
    private javax.swing.JLabel txtDialog;
    // End of variables declaration//GEN-END:variables

    void setTextNuevaApuesta(String string) {
        panelApuesta.setVisible(false);
        lblNuevaApuesta.setText(string);
    }

    void setEsperandoVisible(boolean b) {
        panelEsperando.setVisible(b);
    }

    void setAceptarApuestaVisible(boolean b) {
        panelAceptarApuesta.setVisible(b);
    }

    void setDialogVisible(boolean b) {
        panelDialog.setVisible(b);
    }

    protected void mostrarDialogFinPartida(PartidaPoker partida, Jugador ganador, Jugador jugadorFrame) {
        this.remove(panelAceptarApuesta);
        this.remove(panelApuesta);
        this.remove(panelDialog);
        this.remove(panelEsperando);

        setLayout(new BorderLayout());

        if (ganador == null) {
            add(new PanelFinPartida(framePoker, partida), BorderLayout.CENTER);
        } else {
            add(new PanelFinPartida(framePoker, partida, ganador), BorderLayout.CENTER);
        }
        validate();
        repaint();

        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }

    void deshabilitarTimer() {
        System.out.println("deshabilitar");
        panelTimer.deshabilitarTimer();
    }

    @Override
    public void finalizoTimer() {
        if (showTimer) {
            mostrarPanelEsperando("TIMEOUT, queda fuera de la partida");
        }
        //framePoker.continuarEnJuego(false);
    }
}
