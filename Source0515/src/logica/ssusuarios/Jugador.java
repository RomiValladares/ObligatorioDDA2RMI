/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ssusuarios;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Romi
 */
public interface Jugador extends Usuario {

    double getSaldo() throws RemoteException;

    public String getNombre() throws RemoteException;

    public void agregarSaldo(double pozo) throws RemoteException;

    public void restarSaldo(double monto) throws RemoteException;

    public String getEtiqueta() throws RemoteException;

    public void setSaldo(double aDouble) throws RemoteException;

}
