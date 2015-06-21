package iu;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import logica.JuegoCasino;
import logica.Jugador;

public abstract class FrameJuegoCasino extends JFrame {

    private Jugador jugador;
    // TODO
    private JuegoCasino juego;

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Jugador getJugador() {
        return jugador;
    }

    protected void setImagenFondo(String path) {
        try {
            final Image backgroundImage = javax.imageio.ImageIO.read(new File(path));

            if (backgroundImage != null) {

                setContentPane(new JPanel() {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(backgroundImage, 0, 0, getParent().getWidth(), getParent().getHeight(), null);
                    }
                });
            } else {
                Logger.getLogger(FrameLogin.class.getName()).log(Level.SEVERE, null, "Background image null");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
