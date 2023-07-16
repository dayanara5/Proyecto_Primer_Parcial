package ec.edu.espol.clases;

import static ec.edu.espol.clases.Hash.getSHA;
import java.io.File;
import java.util.Scanner;

public class Utilitaria {

    public static boolean validarContrasena(Comprador p, String filenom) {
        try (Scanner sc = new Scanner(new File(filenom + ".txt"))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split("\\|");
                String contrasena = tokens[4];
                String contraHashP = null;
                try {
                    contraHashP = Hash.toHexString(getSHA(p.getClave()));
                } // For specifying wrong message digest algorithms
                catch (Exception e) {
                    System.out.println("Exception thrown for incorrect algorithm: " + e);
                }
                System.out.println(contrasena);
                System.out.println(contraHashP);
                
                if (contraHashP.equals(contrasena)) {
                    return true;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean validarCorreo(Comprador p, String filenom) {
        
        try (Scanner sc = new Scanner(new File(filenom + ".txt"))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split("\\|");
                String correo = tokens[3];
                if (p.getCorreoElectronico().equals(correo)) {
                    return true;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean inicioSesion(String correo, String contrasena, String filenom) {
        Scanner sc = new Scanner(System.in);

        Comprador comprador = new Comprador(correo, contrasena);
        while (validarCorreo(comprador, filenom) == false) {
            System.out.println("Correo invalido, ingrese uno existente");
            correo = sc.next();
            comprador.setCorreoElectronico(correo);
        }

        while (validarContrasena(comprador, filenom) == false) {
            System.out.println("Contrasena invalida, intentelo de nuevo");
            contrasena = sc.next();
            comprador.setClave(contrasena);
        }

        return ((validarCorreo(comprador, filenom) == true) && (validarContrasena(comprador, filenom) == true));
    }
}
