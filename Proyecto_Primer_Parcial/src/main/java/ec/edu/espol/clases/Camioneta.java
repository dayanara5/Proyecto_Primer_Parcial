/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.clases;

/**
 *
 * @author Axel
 */
public class Camioneta extends Vehiculo{
    private String vidrios;
    private String transmicion;
    private String traccion;

    public Camioneta(String placa, String marca, String modelo, String tipoMotor, int anio, double recorrido, String color, String combustible,String vidrios, String transmicion, String traccion, double precio) {
        super(placa, marca, modelo, tipoMotor, anio, recorrido, color, combustible, precio);
        this.vidrios = vidrios;
        this.transmicion = transmicion;
        this.traccion = traccion;
    }
    
    
}
