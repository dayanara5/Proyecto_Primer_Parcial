/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.clases;

import static ec.edu.espol.clases.Hash.getSHA;
import static ec.edu.espol.clases.Hash.toHexString;
import static ec.edu.espol.clases.Utilitaria.inicioSesion;
import static ec.edu.espol.clases.Utilitaria.validarCorreo;
import static ec.edu.espol.clases.Utilitaria.validarPlaca;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Axel
 */
public class Vendedor extends Persona {

    private ArrayList<Vehiculo> vehiculos;

    public Vendedor(ArrayList<Vehiculo> vehiculos, String nombres, String apellidos, String organizacion, String correoElectronico, String clave) {
        super(nombres, apellidos, organizacion, correoElectronico, clave);
        this.vehiculos = vehiculos;
    }

    public Vendedor(String nombre, String apellidos, String organizacion, String correoElectronico, String clave) {
        super(nombre, apellidos, organizacion, correoElectronico, clave);
    }

    public Vendedor(String correoElectronico, String clave) {
        super(null, null, null, correoElectronico, clave);
        this.vehiculos = null;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public static void menuVendedor() {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        int opcion;

        while (!salir) {
            System.out.println("1.Registrar un nuevo vendedor");
            System.out.println("2.Registrar un nuevo vehiculo");
            System.out.println("3.Aceptar oferta");
            System.out.println("4.Salir");

            try {
                System.out.println("Eliga que desea hacer");
                opcion = sc.nextInt();
                switch (opcion) {
                    case 1:
                        registrarVendedor();
                        break;
                    case 2:
                        registrarVehiculo();
                        break;
                    case 3:

                        break;

                    case 4:
                        salir = true;
                    default:
                        System.out.println("Eliga una opcion valida");

                }
            } catch (Exception e) {
                System.out.println("Debes escribir un numero");
                sc.next();
            }
        }
        System.out.println("Regresando al menu anterior");
    }

    public static Vendedor registrarVendedor() throws NoSuchAlgorithmException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingresar nombres: ");
        String nombres = sc.next();

        System.out.println("Ingresar apellidos: ");
        String apellidos = sc.next();

        System.out.println("Ingresar organización: ");
        String organizacion = sc.next();

        System.out.println("IngresarCorreoElectronico: ");
        String correo = sc.next();

        System.out.println("Ingresar Clave: ");
        String clave = sc.next();
        String claveHash = toHexString(getSHA(clave));

        Vendedor vendedor = new Vendedor(nombres, apellidos, organizacion, correo, claveHash);

        boolean condicion = validarCorreo(vendedor, "vendedores");
        do {
            if (condicion == false) {
                try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("vendedores.txt"), true))) {
                    pw.println(nombres + "|" + apellidos + "|" + organizacion + "|" + correo + "|" + claveHash);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return vendedor;
            } else if (condicion == true) {
                System.out.println("El correo ya ha sido registrado, ingrese uno nuevo");
                System.out.println("Ingrese un correo valido: ");
                correo = sc.nextLine();
                vendedor.setCorreoElectronico(correo);
                condicion = validarCorreo(vendedor, "compradores");
                if (condicion == false) {
                    try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("compradores.txt"), true))) {
                        pw.println(nombres + "|" + apellidos + "|" + organizacion + "|" + correo + "|" + claveHash);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    return vendedor;
                }
            }

        } while (condicion);
        return vendedor;
    }

    public void aceptarOferta(Vehiculo vehi, Oferta ofer) {
        vehiculos.remove(vehi);
        enviarConGMail(ofer.getComprador().getCorreoElectronico(), "Notificación", "Se aceptado la oferta");
    }

    private static void enviarConGMail(String destinatario, String asunto, String cuerpo) {
        //La dirección de correo de envío
        String remitente = "remitente@gmail.com";
        //La clave de aplicación obtenida según se explica en este artículo:
        String claveemail = "1234567890123456";

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", claveemail);    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, claveemail);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
        }
    }

    public static void registrarVehiculo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese correo");
        String correo = sc.nextLine();
        System.out.println("Ingrese contraseña");
        String contrasena = sc.nextLine();

        inicioSesion(correo, contrasena, "vendedores"); //Siempre retornara true por las condiciones del metodo

        Vendedor vendedor = new Vendedor(correo, contrasena);

        System.out.println("Ingrese el tipo de vehiculo: ");
        String tipo = sc.nextLine();

        ArrayList<Vehiculo> veh = new ArrayList<>();

        do {
            if (tipo.equals("auto")) {

                System.out.println(("Ingrese la placa: "));
                String placa = sc.nextLine();
                boolean condicion = validarPlaca(placa);
                do {
                    if (condicion == true) {
                        System.out.println("Ingrese una nueva placa:");
                        placa = sc.nextLine();
                        condicion = validarPlaca(placa);
                    }

                } while (condicion == true);

                System.out.println(("Ingrese la marca: "));
                String marca = sc.nextLine();

                System.out.println(("Ingrese el modelo: "));
                String modelo = sc.nextLine();

                System.out.println(("Ingrese el tipo de motor: "));
                String tipoMotor = sc.nextLine();

                System.out.println(("Ingrese el año: "));
                int anio = sc.nextInt();
                sc.nextLine();

                System.out.println(("Ingrese el kilometraje: "));
                double kilometraje = sc.nextDouble();
                sc.nextLine();

                System.out.println(("Ingrese el color: "));
                String color = sc.nextLine();

                System.out.println(("Ingrese el tipo de combustible: "));
                String tipoCombustible = sc.nextLine();

                System.out.println(("Tiene vidrios? "));
                String vidrios = sc.nextLine();

                System.out.println(("Ingrese transmision: "));
                String transmision = sc.nextLine();

                System.out.println(("Ingrese el precio de su vehiculo: "));
                double precio = sc.nextDouble();
                sc.nextLine();
                
                Auto a = new Auto(placa, marca, modelo, tipoMotor, anio, kilometraje, color, tipoCombustible, vidrios, transmision, precio);

                veh.add(a);

                try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("vehiculos.txt"), true))) {
                    pw.println(placa + "|" + marca + "|" + modelo + "|" + tipoMotor + "|" + anio + "|" + kilometraje + "|" + color + "|" + tipoCombustible + "|" + vidrios + "|" + transmision + "|" + precio);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else if (tipo.equals("moto")) {

                System.out.println(("Ingrese la placa: "));
                String placaMoto = sc.nextLine();
                boolean condicion = validarPlaca(placaMoto);
                do {
                    if (condicion == true) {
                        System.out.println("Ingrese una nueva placa:");
                        placaMoto = sc.nextLine();
                        condicion = validarPlaca(placaMoto);
                    }

                } while (condicion == true);

                System.out.println(("Ingrese la marca: "));
                String marca = sc.nextLine();

                System.out.println(("Ingrese el modelo: "));
                String modelo = sc.nextLine();

                System.out.println(("Ingrese el tipo de motor: "));
                String tipoMotor = sc.nextLine();

                System.out.println(("Ingrese el año: "));
                int anio = sc.nextInt();
                sc.nextLine();

                System.out.println(("Ingrese el kilometraje: "));
                double kilometraje = sc.nextDouble();
                sc.nextLine();
                
                System.out.println(("Ingrese el color: "));
                String color = sc.nextLine();

                System.out.println(("Ingrese el tipo de combustible: "));
                String tipoCombustible = sc.nextLine();

                System.out.println(("Ingrese el precio de su vehiculo: "));
                double precio = sc.nextDouble();
                sc.nextLine();
                
                Vehiculo v = new Vehiculo(placaMoto, marca, modelo, tipoMotor, anio, kilometraje, color, tipoCombustible, precio);

                veh.add(v);

                try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("vehiculos.txt"), true))) {
                    pw.println(placaMoto + "|" + marca + "|" + modelo + "|" + tipoMotor + "|" + anio + "|" + kilometraje + "|" + color + "|" + tipoCombustible + "|" + precio);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else if (tipo.equals("camionetas")) {

                System.out.println(("Ingrese la placa: "));
                String placa = sc.nextLine();

                System.out.println(("Ingrese la placa: "));
                String placaCamioneta = sc.nextLine();
                boolean condicion = validarPlaca(placaCamioneta);
                do {
                    if (condicion == true) {
                        System.out.println("Ingrese una nueva placa:");
                        placaCamioneta = sc.nextLine();
                        condicion = validarPlaca(placaCamioneta);
                    }

                } while (condicion == true);

                System.out.println(("Ingrese la marca: "));
                String marca = sc.nextLine();

                System.out.println(("Ingrese el modelo: "));
                String modelo = sc.nextLine();

                System.out.println(("Ingrese el tipo de motor: "));
                String tipoMotor = sc.nextLine();

                System.out.println(("Ingrese el año: "));
                int anio = sc.nextInt();
                sc.nextLine();

                System.out.println(("Ingrese el kilometraje: "));
                double kilometraje = sc.nextDouble();
                sc.nextLine();
                
                System.out.println(("Ingrese el color: "));
                String color = sc.nextLine();

                System.out.println(("Ingrese el tipo de combustible: "));
                String tipoCombustible = sc.nextLine();

                System.out.println(("Tiene vidrios? "));
                String vidrios = sc.nextLine();

                System.out.println(("Ingrese transmision: "));
                String transmision = sc.nextLine();

                System.out.println(("Ingrese traccion: "));
                String traccion = sc.nextLine();

                System.out.println(("Ingrese el precio de su vehiculo: "));
                double precio = sc.nextDouble();
                sc.nextLine();
                
                Camioneta c = new Camioneta(placa, marca, modelo, tipoMotor, anio, kilometraje, color, tipoCombustible, vidrios, transmision, traccion, precio);

                veh.add(c);

                try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("vehiculos.txt"), true))) {
                    pw.println(placa + "|" + marca + "|" + modelo + "|" + tipoMotor + "|" + anio + "|" + kilometraje + "|" + color + "|" + tipoCombustible + "|" + vidrios + "|" + transmision + "|" + traccion + "|" + precio);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else {
                System.out.println("Ingrese un tipo de vehiculo valido");
            }
        } while (tipo.isBlank());

        
    }


    /* public void revisarOfertas() {
        Scanner sn = new Scanner(System.in);
        System.out.println("Ingrese su correo electronico:");
        String correo_elec = sn.nextLine();
        System.out.println("Ingrese su contraseña:");
        String contra = sn.nextLine();
        boolean condicion = inicioSesion(correo_elec, contra, "vendedores");
        if (condicion) {
            Vehiculo vehiculo_1 = validarPlaca();
            int opcion;
            do {
                int contador = 0;
                Oferta oferta_1 = vehiculo_1.mostrarOferta(contador);
                System.out.println("1. Siguiente oferta");
                System.out.println("2. Aceptar Oferta");
                opcion = sn.nextInt();
                sn.nextLine();
                switch (opcion) {
                    case 1:
                        int i = vehiculo_1.siguienteOferta(contador);
                        contador = i;
                        vehiculo_1.mostrarOferta(contador);
                        break;
                    case 2:
                        aceptarOferta(vehiculo_1, oferta_1);
                        break;
                }
            } 
        }
    }*/
}
