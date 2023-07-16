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

                if (contraHashP.equals(contrasena)) {
                    return true;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean validarContrasena(Vendedor v, String filenom) {
        try (Scanner sc = new Scanner(new File(filenom + ".txt"))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split("\\|");
                String correo = tokens[3];
                String contrasena = tokens[4];
                String contraHashP = null;
                System.out.println(linea);
                
                if ((correo.equals(v.getCorreoElectronico())) == true ) {
                    try {
                        contraHashP = Hash.toHexString(getSHA(v.getClave()));
                    } // For specifying wrong message digest algorithms
                    catch (Exception e) {
                        System.out.println("Exception thrown for incorrect algorithm: " + e);
                    }
                    if (contraHashP.equals(contrasena)) {
                    return true;
                    }
                    
                }
                else if((correo.equals(v.getCorreoElectronico())) == false )
                    sc.nextLine();
                    
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean validarCorreo(Vendedor v, String filenom) {

        try (Scanner sc = new Scanner(new File(filenom + ".txt"))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split("\\|");
                String correo = tokens[3];
                if (v.getCorreoElectronico().equals(correo)) {
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

        if (filenom.equals("vendedores")) {
            Vendedor vendedor = new Vendedor(correo, contrasena);
            while (validarCorreo(vendedor, filenom) == false) {
                System.out.println("Correo invalido, ingrese uno existente");
                correo = sc.nextLine();
                vendedor.setCorreoElectronico(correo);
            }

            while (validarContrasena(vendedor, filenom) == false) {
                System.out.println("Contrasena invalida, intentelo de nuevo");
                contrasena = sc.nextLine();
                vendedor.setClave(contrasena);
            }

            return ((validarCorreo(vendedor, filenom) == true) && (validarContrasena(vendedor, filenom) == true));
        } else if (filenom.equals("compradores")) {
            Comprador comprador = new Comprador(correo, contrasena);
            while (validarCorreo(comprador, filenom) == false) {
                System.out.println("Correo invalido, ingrese uno existente");
                correo = sc.nextLine();
                comprador.setCorreoElectronico(correo);
            }

            while (validarContrasena(comprador, filenom) == false) {
                System.out.println("Contrasena invalida, intentelo de nuevo");
                contrasena = sc.nextLine();
                comprador.setClave(contrasena);
            }

            return ((validarCorreo(comprador, filenom) == true) && (validarContrasena(comprador, filenom) == true));
        }
        return false;
    }

    public static boolean validarPlaca(String placa) {

        try (Scanner sc = new Scanner(new File("vehiculos.txt"))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split("\\|");
                String placaRegistrada = tokens[0];
                if (placa.equals(placaRegistrada)) {

                    return true;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}
