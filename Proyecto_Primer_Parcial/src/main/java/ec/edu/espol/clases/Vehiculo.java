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
public class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private String tipoMotor;
    private int anio;//Año
    private double recorrido;
    private String color;
    private String combustible;
    private double precio;
    private ArrayList<Oferta> ofertas;
    private Vendedor vendedor;

    public Vehiculo(String placa, String marca, String modelo, String tipoMotor, int anio, double recorrido, String color, String combustible, double precio) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.tipoMotor = tipoMotor;
        this.anio = anio;
        this.recorrido = recorrido;
        this.color = color;
        this.combustible = combustible;
        this.precio = precio;

    }
    
}
