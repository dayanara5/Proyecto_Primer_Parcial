/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.edu.espol.proyecto_primer_parcial;

import static ec.edu.espol.clases.Comprador.opcionesComprador;
import static ec.edu.espol.clases.Hash.getSHA;
import static ec.edu.espol.clases.Hash.toHexString;
import static ec.edu.espol.clases.Vendedor.menuVendedor;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 *
 * @author dayan
 */
public class Proyecto_Primer_Parcial {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la opcion");
        System.out.println("1.Vendedor");
        System.out.println("2.Comprador");
        System.out.println("3.Salir");
        int opcion= sc.nextInt();

        do{        
        switch(opcion){
            case 1:
                menuVendedor();
            case 2:
                opcionesComprador();
            case 3:
                opcion = 3;
                
            default:
                System.out.println("Ingrese una opcion valida");
        }
        }while (opcion!=3);
    }
}
