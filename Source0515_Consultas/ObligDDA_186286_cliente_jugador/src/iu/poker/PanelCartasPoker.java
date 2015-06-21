/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iu.poker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import logica.poker.CartaPoker;

/**
 *
 * @author Romi
 */
public class PanelCartasPoker extends javax.swing.JPanel {

    private final String PATH = "src/imgs/cartas";
    private final String IMG_TYPE = "gif";

    private boolean modoDescartar = false;

    /*
     * mapea botones con la carta correspondiente
     */
    private HashMap<JButton, CartaPoker> btnsImages = new HashMap<>();

    /**
     * Creates new form PanelJuegoPoker
     */
    public PanelCartasPoker() {
        initComponents();

        panelDescartarse.setVisible(false);

        carta1.addActionListener(new BtnCartaActionListener());
        carta2.addActionListener(new BtnCartaActionListener());
        carta3.addActionListener(new BtnCartaActionListener());
        carta4.addActionListener(new BtnCartaActionListener());
        carta5.addActionListener(new BtnCartaActionListener());
    }

    private static int maxCartasDescartadas = 4;
    private final String IMG_PATH = "src/imgs/cartas/Invertida.gif";

    private final ImageIcon imgDescartarCarta = new ImageIcon(IMG_PATH);
    private ArrayList<CartaPoker> cartasDescartadas = new ArrayList<>();

    private class BtnCartaActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            if (modoDescartar) {
                if (btn.getIcon().equals(imgDescartarCarta)) {
                    btn.setIcon(getIconCarta(btnsImages.get(btn)));
                    cartasDescartadas.remove(btnsImages.get(btn));
                } else if (cartasDescartadas.size() != maxCartasDescartadas) {
                    cartasDescartadas.add(btnsImages.get(btn));
                    btn.setIcon(imgDescartarCarta);
                }
            }
        }
    }

    public void setCartas(List<CartaPoker> cartas) throws Exception {
        //se estan seteando las cartas por primera vez
        if (cartas.size() == 5) {
            btnsImages = new HashMap<>();
            cartasDescartadas = new ArrayList<>();
            for (CartaPoker carta : cartas) {
                mostrarCarta(carta, cartas.indexOf(carta));
            }
        } else {
            //se estan seteando cartas que se habian descartado
            reemplazarCartas(cartas);
        }
    }

    private void reemplazarCartas(List<CartaPoker> cartas) throws Exception {
        for (CartaPoker carta : cartas) {
            mostrarCarta(carta, getProximoBotonDescartado());
        }
    }

    private int getProximoBotonDescartado() {
        if (carta1.getIcon().equals(imgDescartarCarta)) {
            return 0;
        } else if (carta2.getIcon().equals(imgDescartarCarta)) {
            return 1;
        } else if (carta3.getIcon().equals(imgDescartarCarta)) {
            return 2;
        } else if (carta4.getIcon().equals(imgDescartarCarta)) {
            return 3;
        } else if (carta5.getIcon().equals(imgDescartarCarta)) {
            return 4;
        }
        return -1;
    }

    private ImageIcon getIconCarta(CartaPoker c) {
        String imgPath = PATH + "/" + c.getEtiqueta() + "." + IMG_TYPE;
        return new ImageIcon(imgPath);
    }

    public boolean isModoDescartar() {
        return modoDescartar;
    }
    
    private void mostrarCarta(CartaPoker c, int i) throws Exception {
        ImageIcon img = getIconCarta(c);
        switch (i + 1) {
            case 1:
                btnsImages.put(carta1, c);
                carta1.setIcon(img);
                break;
            case 2:
                btnsImages.put(carta2, c);
                carta2.setIcon(img);
                break;
            case 3:
                btnsImages.put(carta3, c);
                carta3.setIcon(img);
                break;
            case 4:
                btnsImages.put(carta4, c);
                carta4.setIcon(img);
                break;
            case 5:
                btnsImages.put(carta5, c);
                carta5.setIcon(img);
                break;
        }
    }

    /*
     * hace que se muestre el panel para descartarse
     * y que se pueda hacer click en cada carta
     * @param component JFrame interesada en saber cuando el btnDescartar sea clickeado
     */
    public void setModoDescartarse(FramePoker component) {
        setEventoClickDescartar(component);
        setModoDescartarse(true);
    }

    public void setModoDescartarse(boolean set) {
        modoDescartar = set;
        panelDescartarse.setVisible(set);
    }

    private void setEventoClickDescartar(final FramePoker component) {
        for (ActionListener al : btnDescartar.getActionListeners()) {
            btnDescartar.removeActionListener(al);
        }

        btnDescartar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                component.descartarCartas(cartasDescartadas);
                modoDescartar = false;
                panelDescartarse.setVisible(false);
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

        jPanel1 = new javax.swing.JPanel();
        carta2 = new javax.swing.JButton();
        carta3 = new javax.swing.JButton();
        carta4 = new javax.swing.JButton();
        carta1 = new javax.swing.JButton();
        carta5 = new javax.swing.JButton();
        panelDescartarse = new javax.swing.JPanel();
        btnDescartar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setMaximumSize(new java.awt.Dimension(594, 211));
        jPanel1.setMinimumSize(new java.awt.Dimension(594, 211));
        jPanel1.setOpaque(false);

        carta2.setBorder(null);
        carta2.setBorderPainted(false);
        carta2.setContentAreaFilled(false);
        carta2.setIconTextGap(0);
        carta2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        carta3.setBorder(null);
        carta3.setBorderPainted(false);
        carta3.setContentAreaFilled(false);
        carta3.setIconTextGap(0);
        carta3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        carta4.setBorder(null);
        carta4.setBorderPainted(false);
        carta4.setContentAreaFilled(false);
        carta4.setIconTextGap(0);
        carta4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        carta1.setBorder(null);
        carta1.setBorderPainted(false);
        carta1.setContentAreaFilled(false);
        carta1.setIconTextGap(0);
        carta1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        carta5.setBorder(null);
        carta5.setBorderPainted(false);
        carta5.setContentAreaFilled(false);
        carta5.setIconTextGap(0);
        carta5.setMargin(new java.awt.Insets(0, 0, 0, 0));

        btnDescartar.setBackground(new java.awt.Color(255, 153, 0));
        btnDescartar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnDescartar.setText("Descartar");

        jLabel1.setText("Haga click en las cartas (max. 4) que desee descartar.");

        javax.swing.GroupLayout panelDescartarseLayout = new javax.swing.GroupLayout(panelDescartarse);
        panelDescartarse.setLayout(panelDescartarseLayout);
        panelDescartarseLayout.setHorizontalGroup(
            panelDescartarseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDescartarseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDescartar))
        );
        panelDescartarseLayout.setVerticalGroup(
            panelDescartarseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDescartarseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnDescartar)
                .addComponent(jLabel1))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(carta1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(carta2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(carta3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(carta4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(carta5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelDescartarse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(carta5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carta1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carta4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carta3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carta2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelDescartarse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDescartar;
    private javax.swing.JButton carta1;
    private javax.swing.JButton carta2;
    private javax.swing.JButton carta3;
    private javax.swing.JButton carta4;
    private javax.swing.JButton carta5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelDescartarse;
    // End of variables declaration//GEN-END:variables

}

