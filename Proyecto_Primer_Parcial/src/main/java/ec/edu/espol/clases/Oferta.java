/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.clases;

import java.util.ArrayList;

/**
 *
 * @author Axel
 */
public class Oferta {
    private Comprador comprador;
    private Vehiculo vehiculo;
    private double precio;

    public Oferta(Comprador comprador, Vehiculo vehiculo, double precio) {
        this.comprador = comprador;
        this.vehiculo = vehiculo;
        this.precio = precio;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
}
