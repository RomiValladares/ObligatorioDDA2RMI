/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package observableremoto;

import java.util.Observable;

/**
 *
 * @author DocenteFI
 */
public class ObservableLocal extends Observable{
    
    public void avisar(Object param){
        setChanged();
        notifyObservers(param);
    }
    
}
