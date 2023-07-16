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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    private ArrayList<Vehiculo> vehiculos = null;

    public Vendedor(ArrayList<Vehiculo> vehiculos, String nombres, String apellidos, String organizacion, String correoElectronico, String clave) {
        super(nombres, apellidos, organizacion, correoElectronico, clave);
        this.vehiculos = vehiculos;
    }

    public Vendedor(String nombre, String apellidos, String organizacion, String correoElectronico, String clave) {
        super(nombre, apellidos, organizacion, correoElectronico, clave);

    }

    public Vendedor(String correoElectronico, String clave) {
        super(null, null, null, correoElectronico, clave);
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
        String nombres = sc.nextLine();

        System.out.println("Ingresar apellidos: ");
        String apellidos = sc.nextLine();

        System.out.println("Ingresar organización: ");
        String organizacion = sc.nextLine();

        System.out.println("IngresarCorreoElectronico: ");
        String correo = sc.nextLine();

        System.out.println("Ingresar Clave: ");
        String clave = sc.nextLine();
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

    public void aceptarOferta(Vehiculo vehi, Oferta ofer) throws FileNotFoundException, IOException {

        enviarConGMail(ofer.getComprador().getCorreoElectronico(), "Notificación", "Se aceptado la oferta");

        vehiculos.remove(vehi);
        String lineToRemove = null;

        try (Scanner sc = new Scanner(new File("vehiculos.txt"))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split("\\|");
                if (tokens[0].equals(vehi.getPlaca())) {
                    lineToRemove = linea;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        File inputFile = new File("vehiculos.txt");
        File tempFile = new File("myTempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if (trimmedLine.equals(lineToRemove)) {
                continue;
            }
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean successful = tempFile.renameTo(inputFile);

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

    public static Vendedor registrarVehiculo() {
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
                vendedor.setVehiculos(veh);

                try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("vehiculos.txt"), true))) {
                    pw.println(placa + "|" + marca + "|" + modelo + "|" + tipoMotor + "|" + anio + "|" + kilometraje + "|" + color + "|" + tipoCombustible + "|" + vidrios + "|" + transmision + "|" + precio);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return vendedor;
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
                vendedor.setVehiculos(veh);

                try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("vehiculos.txt"), true))) {
                    pw.println(placaMoto + "|" + marca + "|" + modelo + "|" + tipoMotor + "|" + anio + "|" + kilometraje + "|" + color + "|" + tipoCombustible + "|" + precio);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                return vendedor;

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
                vendedor.setVehiculos(veh);

                try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("vehiculos.txt"), true))) {
                    pw.println(placa + "|" + marca + "|" + modelo + "|" + tipoMotor + "|" + anio + "|" + kilometraje + "|" + color + "|" + tipoCombustible + "|" + vidrios + "|" + transmision + "|" + traccion + "|" + precio);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return vendedor;
            } else {
                System.out.println("Ingrese un tipo de vehiculo valido");
            }
        } while (tipo.isBlank());

        return null;
    }

    public static void revisarOfertas() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese correo");
        String correo = sc.nextLine();
        System.out.println("Ingrese contraseña");
        String contrasena = sc.nextLine();

        inicioSesion(correo, contrasena, "vendedores"); //Siempre retornara true por las condiciones del metodo

        Vendedor v = new Vendedor(correo, contrasena);

        System.out.println(("Ingrese la placa: "));
        String placa = sc.nextLine();
        boolean condicion = validarPlaca(placa);
        System.out.println(condicion);
        do {
            if (condicion == false) {
                System.out.println("Ingrese una nueva placa:");
                placa = sc.nextLine();
                condicion = validarPlaca(placa);
            }

        } while (condicion == false);

        if (validarPlaca(placa)) {
            for (Vehiculo vehiculo : v.getVehiculos()) {
                if (vehiculo.getPlaca().equals(placa)) {
                    System.out.println(vehiculo.getModelo());
                    System.out.println("Precio: " + vehiculo.getPrecio());
                    int opcion;
                    int i = 0;
                    ArrayList<Oferta> ofertas = vehiculo.getOfertas();
                    System.out.println("Se han realizado" + ofertas.size() + "ofertas");
                    if (i < ofertas.size()) {
                        System.out.println("Correo: " + ofertas.get(i).getComprador().getCorreoElectronico());
                        System.out.println("Precio ofertado: " + ofertas.get(i).getPrecio());
                        if ((i < (ofertas.size() - 1)) && (i != 0)) { //limitando la opcion de avanzar solo hasta el penultimo item

                            System.out.println("1.Siguiente opcion");
                            System.out.println("2.Anterior opcion");
                            System.out.println("3.Aceptar oferta");

                            do {
                                opcion = sc.nextInt();
                                switch (opcion) {
                                    case 1:
                                        i += 1;
                                        break;
                                    case 2:
                                        i -= 1;
                                        break;
                                    case 3:
                                        v.aceptarOferta(vehiculo, ofertas.get(i));
                                        break;
                                    default:
                                        System.out.println("Ingresa una opcion valida");
                                        opcion = 10;
                                }
                            } while (opcion == 10);

                        } else if (i == 0) { //limitando la opcion de solo avanzar en el primer item

                            System.out.println("1.Siguiente opcion");

                            System.out.println("2.Aceptar oferta");

                            do {
                                opcion = sc.nextInt();
                                switch (opcion) {
                                    case 1:
                                        i += 1;
                                        break;
                                    case 2:
                                        v.aceptarOferta(vehiculo, ofertas.get(i));
                                        break;
                                    default:
                                        System.out.println("Ingresa una opcion valida");
                                        opcion = 10;
                                }
                            } while (opcion == 10);

                        } else if (i == ofertas.size() - 1) { //limita a que solo retroceda en el ultimo item
                            System.out.println("1.Anterior opcion");

                            System.out.println("2.Realizar oferta");

                            do {
                                opcion = sc.nextInt();
                                switch (opcion) {
                                    case 1:
                                        i -= 1;
                                        break;
                                    case 2:
                                        v.aceptarOferta(vehiculo, ofertas.get(i));
                                        break;
                                    default:
                                        System.out.println("Ingresa una opcion valida");
                                        opcion = 10;
                                }
                            } while (opcion == 10);

                        }
                    }

                }
            }
        }

    }
}
