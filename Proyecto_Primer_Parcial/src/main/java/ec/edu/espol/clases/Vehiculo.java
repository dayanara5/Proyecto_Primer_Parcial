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
    private String motocicletas;

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

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getTipoMotor() {
        return tipoMotor;
    }

    public int getAnio() {
        return anio;
    }

    public double getRecorrido() {
        return recorrido;
    }

    public String getColor() {
        return color;
    }

    public String getCombustible() {
        return combustible;
    }

    public double getPrecio() {
        return precio;
    }

    public ArrayList<Oferta> getOfertas() {
        return ofertas;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public String getMotocicletas() {
        return motocicletas;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setTipoMotor(String tipoMotor) {
        this.tipoMotor = tipoMotor;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setRecorrido(double recorrido) {
        this.recorrido = recorrido;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setOfertas(ArrayList<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public void setMotocicletas(String motocicletas) {
        this.motocicletas = motocicletas;
    }
    public Oferta mostrarOferta(){
        Oferta ele_oferta= ofertas.get(0);
    }
}
