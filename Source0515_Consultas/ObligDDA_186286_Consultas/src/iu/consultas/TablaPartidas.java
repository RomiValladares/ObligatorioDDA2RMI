/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu.consultas;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import logica.ssjuegos.DatosPartidaJuegoCasino;
import logica.ssjuegos.JuegoCasino;

/**
 *
 * @author Romi
 */
public class TablaPartidas extends javax.swing.JPanel {

    /**
     * para acceder a los participantes cuando se haga click en la tabla de
     * arriba
     */
    private ArrayList<DatosPartidaJuegoCasino> partidas;
    private ArrayList<JuegoCasino> juegos;

    /**
     * Creates new form TablaPartidas
     */
    public TablaPartidas() {
        initComponents();
        tablaPartidas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                Object partidaSeleccionada = tablaPartidas.getValueAt(tablaPartidas.getSelectedRow(), 0);
                lblParticipantesPartida.setText("Participantes partida " + partidaSeleccionada);

                mostrarListaParticipantes(partidas.get(tablaPartidas.getSelectedRow()));
            }
        });
    }

    private void mostrarListaParticipantes(DatosPartidaJuegoCasino seleccionada) {
        listaParticipantes.setListData(new ArrayList<>(seleccionada.getJugadores().keySet()).toArray());
        try {
            lblGanador.setText(seleccionada.getGanador().toString());
        } catch (Exception ex) {
            Logger.getLogger(TablaPartidas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPartidasJuego = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPartidas = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        lblParticipantesPartida = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboJuegos = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaParticipantes = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        lblGanador = new javax.swing.JLabel();

        lblPartidasJuego.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPartidasJuego.setText("Partidas juego {juego}");

        tablaPartidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Numero", "Comienzo", "Final", "Duracion (min)", "Total Apostado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaPartidas);

        lblParticipantesPartida.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblParticipantesPartida.setText("Participantes partida {n}");

        jLabel2.setText("Seleccione un juego:");

        comboJuegos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboJuegos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboJuegosActionPerformed(evt);
            }
        });

        listaParticipantes.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(listaParticipantes);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Ganador:");

        lblGanador.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblGanador.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(lblPartidasJuego, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblParticipantesPartida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblGanador, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(104, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboJuegos, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboJuegos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(lblPartidasJuego)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblParticipantesPartida)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblGanador)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void comboJuegosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboJuegosActionPerformed
        actualizarLabelPartida();
    }//GEN-LAST:event_comboJuegosActionPerformed

    private void actualizarLabelPartida() {
        try {
            lblPartidasJuego.setText("Partidas Juego " + juegos.get(comboJuegos.getSelectedIndex()).getNombre());
        } catch (RemoteException ex) {
            Logger.getLogger(TablaPartidas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox comboJuegos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblGanador;
    private javax.swing.JLabel lblParticipantesPartida;
    private javax.swing.JLabel lblPartidasJuego;
    private javax.swing.JList listaParticipantes;
    private javax.swing.JTable tablaPartidas;
    // End of variables declaration//GEN-END:variables

    JuegoCasino getJuegoSeleccionado() {
        return juegos.get(comboJuegos.getSelectedIndex());
    }

    void actualizarJuegos(ArrayList<JuegoCasino> juegos) {
        this.juegos = juegos;

        comboJuegos.setModel(new DefaultComboBoxModel(juegos.toArray()));
        comboJuegos.setRenderer(new RendererCelda());

        actualizarLabelPartida();
    }

    private final String[] columnasTablaPartidas = {"Numero", "Comienzo", "Final", "Duracion (mins)", "Total Apostado"};

    void actualizarPartidas(ArrayList<DatosPartidaJuegoCasino> partidas) {
        this.partidas = partidas;

        Object[][] objs = new Object[partidas.size()][columnasTablaPartidas.length];
        int fila = 0;
        for (DatosPartidaJuegoCasino partidaJuegoCasino : partidas) {
            String[] datos = new String[]{"" + partidaJuegoCasino.getNumeroPartida(),
                "" + partidaJuegoCasino.getComienzo(),
                "" + partidaJuegoCasino.getFinal(),
                "" + partidaJuegoCasino.getDuracion(),
                "" + partidaJuegoCasino.getTotalApostado()};
            objs[fila++] = armarFila(columnasTablaPartidas.length, datos);
//            objs[fila][col++] = partidaJuegoCasino.getNumeroPartida();
//            objs[fila][col++] = partidaJuegoCasino.getComienzo();
//            objs[fila][col++] = partidaJuegoCasino.getFinal();
//            objs[fila][col++] = partidaJuegoCasino.getDuracion();
//            objs[fila][col++] = partidaJuegoCasino.getTotalApostado();
        }
        tablaPartidas.setModel(new DefaultTableModel(objs, columnasTablaPartidas));
    }

    private Object[] armarFila(int cols, String[] datos) {
        Object[] fila = new Object[cols];
//        for (int i = 0; i < cols; i++) {
//            fila[i] = datos[i];
//        }
        System.arraycopy(datos, 0, fila, 0, cols);
        return fila;
    }
}
