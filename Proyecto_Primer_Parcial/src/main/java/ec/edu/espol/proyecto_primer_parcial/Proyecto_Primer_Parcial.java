/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package ec.edu.espol.proyecto_primer_parcial;

import ec.edu.espol.clases.Comprador;
import static ec.edu.espol.clases.Comprador.buscarVehiculo;
import static ec.edu.espol.clases.Comprador.opcionesComprador;
import static ec.edu.espol.clases.Hash.getSHA;
import static ec.edu.espol.clases.Hash.toHexString;
import ec.edu.espol.clases.Vehiculo;
import static ec.edu.espol.clases.Vendedor.menuVendedor;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author dayan
 */
public class Proyecto_Primer_Parcial {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("---------------------------------");
            System.out.println("-Menu de Opciones-");
            System.out.println("1.Vendedor");
            System.out.println("2.Comprador");
            System.out.println("3.Salir");
            System.out.println("---------------------------------");
            System.out.println("Ingrese la opcion: ");
            opcion= sc.nextInt();
            switch (opcion) {
                case 1:
                    menuVendedor();
                    break;
                case 2:
                    opcionesComprador();
                    break;
                case 3:
                    opcion = 3;
                    break;
                default:
                    
                    System.out.println("Ingrese una opcion valida ");
            }
        } while (opcion != 3);
    
    }
    }


