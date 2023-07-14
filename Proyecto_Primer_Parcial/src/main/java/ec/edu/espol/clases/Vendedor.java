/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.clases;
import static ec.edu.espol.clases.Hash.getSHA;
import static ec.edu.espol.clases.Hash.toHexString;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Axel
 */
public class Vendedor extends Persona{
    private ArrayList<Vehiculo> vehiculos;

    public Vendedor(ArrayList<Vehiculo> vehiculos, String nombres, String apellidos, String organizacion, String correoElectronico, String clave) {
        super(nombres, apellidos, organizacion, correoElectronico, clave);
        this.vehiculos = vehiculos;
    }

    public Vendedor(String nombre, String apellidos, String organizacion, String correoElectronico, String clave) {
        super(nombre, apellidos, organizacion, correoElectronico, clave);
    }
    
    
    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }
    
    public void menuVendedor() throws NoSuchAlgorithmException{
        System.out.println("1. Registrar nuevo Vendedor");
        System.out.println("2. Registrar un nuevo Vehiculo");
        System.out.println("3. Aceptar oferta");
        System.out.println("4. Regresar");
        
        Scanner entrada = new Scanner(System.in);
        int respuesta = entrada.nextInt();
        
        switch(respuesta){
            case 1:
                registrarVendedor();
                break;
                
            case 2:
                
                break;
                
            case 3:
                
                break;
            
            case 4:
                
                break;
        }
        
    }
    
    public Vendedor registrarVendedor() throws NoSuchAlgorithmException{
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Ingresar nombres: ");
        String nombres= sc.next();
        
        System.out.println("Ingresar apellidos: ");
        String apellidos = sc.next();
        
        System.out.println("Ingresar organización: ");
        String  organizacion = sc.next();
        
        System.out.println("IngresarCorreoElectronico: ");
        String  correo = sc.next();
        
        //Validar Correo
        
        System.out.println("Ingresar Clave: ");
        String clave =sc.next();
        String claveHash= toHexString(getSHA(clave));
        
        Vendedor vendedor = new Vendedor(nombres, apellidos, organizacion, correo, claveHash );
        return vendedor;
    }
    
}
