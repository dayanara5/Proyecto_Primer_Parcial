/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.clases;

/**
 *
 * @author Axel
 */
public class Auto extends Vehiculo{
    private String vidrios;
    private String transmision;

    public Auto(String placa, String marca, String modelo, String tipoMotor, int anio, double recorrido, String color, String combustible, String vidrios, String transmision, double precio) {
        super(placa, marca, modelo, tipoMotor, anio, recorrido, color, combustible, precio);
        this.vidrios = vidrios;
        this.transmision = transmision;
    }

    public String getVidrios() {
        return vidrios;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setVidrios(String vidrios) {
        this.vidrios = vidrios;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }
    
}
