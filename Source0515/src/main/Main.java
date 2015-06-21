/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import logica.FachadaV1;

/**
 *
 * @author Romi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        FrameCasino frameCasino = new FrameCasino();
//        frameCasino.setVisible(true);

        Publicador.publicar("casino", FachadaV1.getInstancia());
    }

}
