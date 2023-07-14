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
public class Comprador extends Persona{
    private ArrayList<Oferta> ofertas;

    public Comprador(ArrayList<Oferta> ofertas, String nombre, String apellidos, String organizacion, String correoElectronico, String clave) {
        super(nombre, apellidos, organizacion, correoElectronico, clave);
        this.ofertas = ofertas;
    }
    
    
    
}
