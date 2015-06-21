/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author DocenteFI
 */
public class Publicador {
    
    public static void publicar(String nombre, Remote objeto){
        
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }
        try{
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Naming.rebind(nombre, objeto);
            System.out.println("Publicacion lista!");
        }catch(Exception e){
            System.out.println("Error :" + e);
        }
        
    }
    
}
