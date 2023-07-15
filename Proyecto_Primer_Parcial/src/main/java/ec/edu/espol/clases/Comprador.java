/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.clases;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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
    public Comprador registrarComprador(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese nombres: ");
        String nombres= sc.next();

        System.out.println("Ingrese apellidos: ");
        String apellidos = sc.next();

        System.out.println("Ingrese organización: ");
        String  org = sc.next();

        System.out.println("Ingrese su correo electronico: ");
        String  correo = sc.next();
        
        System.out.println("Ingrese la contraseña para su cuenta");
        String  contrasena = sc.next();
        
        Comprador Ncomprador= new Comprador(null,nombres, 
                                apellidos,org,
                                correo,contrasena );
        do{
            if (validarCorreo(Ncomprador) == false){
                try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("compradores.txt"),true)))
                {
                    pw.println(nombres+"|"+apellidos+"|"+org+"|"+correo+"|"+contrasena);
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
                return Ncomprador;
            }
            else
                System.out.println("El correo ya ha sido registrado, ingrese uno nuevo");
        }while(validarCorreo(Ncomprador) == true);
      return null;      
    }
    
    
    public ArrayList<Vehiculo> buscarVehiculo(String tipo, String recorrido, String ano, String precio){
        ArrayList<Vehiculo> vehiculos = new ArrayList<>();
        String[] recorridoRango= recorrido.split("-");
        String[] anoRango= ano.split("-");
        String[] precioRango= precio.split("-");
        try(Scanner sc = new Scanner(new File("vehiculos.txt"))){
            while(sc.hasNextLine())
            {
                String linea = sc.nextLine();
                String[] tokens= linea.split("\\|");
                int tamano= tokens.length;
                int tipovehiculo= 0;
                do{
                if (tipo.equals("auto"))
                    tipovehiculo= 11;
                else if (tipo.equals("moto"))
                    tipovehiculo= 9;
                else if (tipo.equals("camionetas"))
                    tipovehiculo= 12;
                else if (tipo.isBlank())
                    tipovehiculo= 1;
                else
                        System.out.println("Ingrese un tipo de vehiculo valido");
                }
                while(tipovehiculo ==0);
                    
                if((tamano == 11) && (tipovehiculo == 11)){ //#de atributos que tendran los autos
                   if((Integer.parseInt(recorridoRango[0]) <= Integer.parseInt(tokens[5]))
                        || (Integer.parseInt(recorridoRango[1]) >= Integer.parseInt(tokens[5]))){
                        if((Integer.parseInt(anoRango[0]) <= Integer.parseInt(tokens[4]))
                        || (Integer.parseInt(anoRango[1]) >= Integer.parseInt(tokens[4]))){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Vehiculo vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);
                            }
                            else if(precio.isBlank()){
                                Vehiculo vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);                                                     
                            }
                   }
                        else if(ano.isBlank()){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Vehiculo vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);
                            }
                            }
                            else if(precio.isBlank()){
                                Vehiculo vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);
                            }
                   }
                   else if (recorrido.isBlank()){
                       if((Integer.parseInt(anoRango[0]) <= Integer.parseInt(tokens[4]))
                        || (Integer.parseInt(anoRango[1]) >= Integer.parseInt(tokens[4]))){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Vehiculo vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);
                            }
                            else if(precio.isBlank()){
                                Vehiculo vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);                                                    
                            }
                   }
                        else if(ano.isBlank()){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Vehiculo vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);
                            }
                            }
                            else if(precio.isBlank()){
                                Vehiculo vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);
                            }
                       
                   }
                }
                      
                if((tamano == 9) && (tipovehiculo == 9)){ //#de atributos que tendran las motos
                   if((Integer.parseInt(recorridoRango[0]) <= Integer.parseInt(tokens[5]))
                        || (Integer.parseInt(recorridoRango[1]) >= Integer.parseInt(tokens[5]))){
                        if((Integer.parseInt(anoRango[0]) <= Integer.parseInt(tokens[4]))
                        || (Integer.parseInt(anoRango[1]) >= Integer.parseInt(tokens[4]))){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Vehiculo vehiculo = new Vehiculo(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],Double.parseDouble(tokens[8]));
                                vehiculos.add(vehiculo);
                            }
                            else if(precio.isBlank()){
                                Vehiculo vehiculo = new Vehiculo(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],Double.parseDouble(tokens[8]));
                                vehiculos.add(vehiculo);                                                     
                            }
                   }
                        else if(ano.isBlank()){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Vehiculo vehiculo = new Vehiculo(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],Double.parseDouble(tokens[8]));
                                vehiculos.add(vehiculo);
                            }
                            }
                            else if(precio.isBlank()){
                                Vehiculo vehiculo = new Vehiculo(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],Double.parseDouble(tokens[8]));
                                vehiculos.add(vehiculo);
                            }
                   }
                   else if (recorrido.isBlank()){
                       if((Integer.parseInt(anoRango[0]) <= Integer.parseInt(tokens[4]))
                        || (Integer.parseInt(anoRango[1]) >= Integer.parseInt(tokens[4]))){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Vehiculo vehiculo = new Vehiculo(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],Double.parseDouble(tokens[8]));
                                vehiculos.add(vehiculo);
                            }
                            else if(precio.isBlank()){
                                Vehiculo vehiculo = new Vehiculo(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],Double.parseDouble(tokens[8]));
                                vehiculos.add(vehiculo);                                                   
                            }
                   }
                        else if(ano.isBlank()){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Vehiculo vehiculo = new Vehiculo(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],Double.parseDouble(tokens[8]));
                                vehiculos.add(vehiculo);
                            }
                            }
                            else if(precio.isBlank()){
                                Vehiculo vehiculo = new Vehiculo(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],Double.parseDouble(tokens[8]));
                                vehiculos.add(vehiculo);
                            }
                   }
                }                  
                if((tamano == 12) && (tipovehiculo == 12)){ //#de atributos que tendran las camionetas
                   if((Integer.parseInt(recorridoRango[0]) <= Integer.parseInt(tokens[5]))
                        || (Integer.parseInt(recorridoRango[1]) >= Integer.parseInt(tokens[5]))){
                        if((Integer.parseInt(anoRango[0]) <= Integer.parseInt(tokens[4]))
                        || (Integer.parseInt(anoRango[1]) >= Integer.parseInt(tokens[4]))){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Vehiculo vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);
                            }
                            else if(precio.isBlank()){
                                Vehiculo vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);                                                    
                            }
                   }
                        else if(ano.isBlank()){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Vehiculo vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);
                            }
                            }
                            else if(precio.isBlank()){
                                Vehiculo vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);
                            }
                   }
                   else if (recorrido.isBlank()){
                       if((Integer.parseInt(anoRango[0]) <= Integer.parseInt(tokens[4]))
                        || (Integer.parseInt(anoRango[1]) >= Integer.parseInt(tokens[4]))){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Vehiculo vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);
                            }
                            else if(precio.isBlank()){
                                Vehiculo vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);                                                   
                            }
                   }
                        else if(ano.isBlank()){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Vehiculo vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);
                            }
                            }
                            else if(precio.isBlank()){
                                Vehiculo vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);
                            }
                   }
                }                           
            }
          return vehiculos;
        
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
        }    
        
    
    public void hacerOferta(Vehiculo v){
        
    }
    
    public static boolean validarCorreo(Comprador p){
        try(Scanner sc = new Scanner(new File("compradores.txt")) ){
            while(sc.hasNextLine())
            {
                String linea = sc.nextLine();
                String[] tokens= linea.split("\\|");
                String correo = tokens[3];
                if(p.getCorreoElectronico().equals(correo))
                    return true;                
                                           
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    
}
