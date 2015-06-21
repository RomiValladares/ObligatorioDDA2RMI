/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

/**
 *
 * @author Romi clase para modelar las entradas simples de la tabla Parametros
 * como oid, ganancias
 */
public class Parametro {

    private Object nombre;
    private double valor;

    public Parametro() {
    }

    public Parametro(Object nombre, double valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public Object getNombre() {
        return nombre;
    }

    public void setNombre(Object nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
