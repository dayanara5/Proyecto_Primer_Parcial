
package ec.edu.espol.clases;

import java.io.File;
import java.util.Scanner;


public class Utilitaria {
    public static boolean validarContrasena(Comprador p, String filenom){
        try(Scanner sc = new Scanner(new File(filenom+".txt")) ){
            while(sc.hasNextLine())
            {
                String linea = sc.nextLine();
                String[] tokens= linea.split("\\|");
                String correo = tokens[5];
                if(p.getClave().equals(correo))
                    return true;                
                                           
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static boolean validarCorreo(Comprador p, String filenom){
        try(Scanner sc = new Scanner(new File(filenom+".txt")) ){
            while(sc.hasNextLine())
            {
                String linea = sc.nextLine();
                String[] tokens= linea.split("\\|");
                String correo = tokens[4];
                if(p.getCorreoElectronico().equals(correo))
                    return true;                
                                           
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static boolean inicioSesion(String correo, String contrasena, String filenom) {
        Scanner sc = new Scanner(System.in);
        
            System.out.println("Ingrese correo");
            System.out.println("Ingrese contraseña");
            correo= sc.next();
            contrasena= sc.next();
            Comprador comprador= new Comprador(correo, contrasena);
            while (validarCorreo(comprador, filenom) == false){
                System.out.println("Correo invalido, ingrese uno existente");
                correo= sc.next();
                comprador.setCorreoElectronico(correo);
            }
            
            while(validarContrasena(comprador, filenom)== false){
                System.out.println("Contrasena invalida, intentelo de nuevo");
                contrasena = sc.next();
                comprador.setClave(contrasena);
            }
            
     return ((validarCorreo(comprador,filenom) == true)&& (validarContrasena(comprador, filenom)==true));
    }
}
