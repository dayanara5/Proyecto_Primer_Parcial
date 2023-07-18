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

    private ArrayList<Vehiculo> vehiculos;

    public Vendedor(ArrayList<Vehiculo> vehiculos, String nombres, String apellidos, String organizacion, String correoElectronico, String clave) {
        super(nombres, apellidos, organizacion, correoElectronico, clave);
        this.vehiculos = vehiculos;
    }

    public Vendedor(String nombre, String apellidos, String organizacion, String correoElectronico, String clave) {
        super(nombre, apellidos, organizacion, correoElectronico, clave);
        this.vehiculos = new ArrayList<>();

    }

    public Vendedor(String correoElectronico, String clave) {
        super(null, null, null, correoElectronico, clave);
        this.vehiculos = new ArrayList<>();
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public static void menuVendedor() throws NoSuchAlgorithmException, IOException {
        Scanner sc = new Scanner(System.in);

        int opcion;

        do {
            System.out.println("1.Registrar un nuevo vendedor");
            System.out.println("2.Registrar un nuevo vehiculo");
            System.out.println("3.Revisar oferta");
            System.out.println("4.Salir");

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
                    revisarOfertas();
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Eliga una opcion valida");

            }
        } while (opcion != 4);

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
        String remitente = "smorales508@gmail.com";
        //La clave de aplicación obtenida según se explica en este artículo:
        String claveemail = "vqcjjtkaxcjczhaz";

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
                vendedor.getVehiculos().add(a);

                try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("vehiculos.txt"), true))) {
                    pw.println(placa + "|" + marca + "|" + modelo + "|" + tipoMotor + "|" + anio + "|" + kilometraje + "|" + color + "|" + tipoCombustible + "|" + vidrios + "|" + transmision + "|" + precio + "|" + vendedor.getCorreoElectronico());
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
                vendedor.setVehiculos(veh);

                try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("vehiculos.txt"), true))) {
                    pw.println(placaMoto + "|" + marca + "|" + modelo + "|" + tipoMotor + "|" + anio + "|" + kilometraje + "|" + color + "|" + tipoCombustible + "|" + precio + "|" + vendedor.getCorreoElectronico());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else if (tipo.equals("camioneta")) {

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

                Camioneta c = new Camioneta(placaCamioneta, marca, modelo, tipoMotor, anio, kilometraje, color, tipoCombustible, vidrios, transmision, traccion, precio);

                veh.add(c);
                vendedor.setVehiculos(veh);

                try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("vehiculos.txt"), true))) {
                    pw.println(placaCamioneta + "|" + marca + "|" + modelo + "|" + tipoMotor + "|" + anio + "|" + kilometraje + "|" + color + "|" + tipoCombustible + "|" + vidrios + "|" + transmision + "|" + traccion + "|" + precio + "|" + vendedor.getCorreoElectronico());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else {
                System.out.println("Ingrese un tipo de vehiculo valido");
            }
        } while (tipo.isBlank());

    }

    public static void revisarOfertas() throws IOException {
        Scanner sc = new Scanner(System.in);

        ArrayList<Vehiculo> vehiculosT = new ArrayList<>();
        Vehiculo moto = new Vehiculo(null, null, null, null, 0, 0., null, null, 0.);
        Auto auto = new Auto(null, null, null, null, 0, 0., null, null, null, null, 0.);
        Camioneta camioneta = new Camioneta(null, null, null, null, 0, 0., null, null, null, null, null, 0.);

        System.out.println("Ingrese correo");
        String correo = sc.nextLine();
        System.out.println("Ingrese contraseña");
        String contrasena = sc.nextLine();

        inicioSesion(correo, contrasena, "vendedores"); //Siempre retornara true por las condiciones del metodo
        String correoIgual = null;
        String placaVendedor;

        System.out.println(("Ingrese la placa: "));
        String placa = sc.nextLine();

        boolean condicion = validarPlaca(placa);

        Vendedor v = new Vendedor(correoIgual, contrasena);

        try (Scanner reader = new Scanner((new File("vehiculos.txt")))) {
            while (reader.hasNextLine()) {
                String linea = reader.nextLine();
                String[] tokens = linea.split("\\|");
                switch (tokens.length) {
                    case 10 -> {
                        //si es moto
                        if (correo.equals(tokens[9])) {
                            v.setCorreoElectronico(correo);
                            if (placa.equals(tokens[0])) {
                                placaVendedor = tokens[0];
                                moto.setPlaca(placaVendedor);
                                moto.setMarca(tokens[1]);
                                moto.setModelo(tokens[2]);
                                moto.setTipoMotor(tokens[3]);
                                moto.setAnio(Integer.parseInt(tokens[4]));
                                moto.setRecorrido(Double.parseDouble(tokens[5]));
                                moto.setColor(tokens[6]);
                                moto.setCombustible(tokens[7]);
                                moto.setPrecio(Double.parseDouble(tokens[8]));
                                moto.setVendedor(v);
                                vehiculosT.add(moto);
                                break;
                            }
                        }
                    }
                    case 12 -> {
                        //si es auto
                        if (correo.equals(tokens[11])) {
                            v.setCorreoElectronico(correo);
                            if (placa.equals(tokens[0])) {
                                placaVendedor = tokens[0];
                                auto.setPlaca(placaVendedor);
                                auto.setMarca(tokens[1]);
                                auto.setModelo(tokens[2]);
                                auto.setTipoMotor(tokens[3]);
                                auto.setAnio(Integer.parseInt(tokens[4]));
                                auto.setRecorrido(Double.parseDouble(tokens[5]));
                                auto.setColor(tokens[6]);
                                auto.setCombustible(tokens[7]);
                                auto.setVidrios(tokens[8]);
                                auto.setTransmision(tokens[9]);
                                auto.setPrecio(Double.parseDouble(tokens[10]));
                                auto.setVendedor(v);
                                vehiculosT.add(auto);
                                break;
                            }
                        }
                    }
                    case 13 -> {
                        //si es camioneta
                        if (correo.equals(tokens[12])) {
                            v.setCorreoElectronico(correo);
                            if (placa.equals(tokens[0])) {
                                placaVendedor = tokens[0];
                                camioneta.setPlaca(placaVendedor);
                                camioneta.setMarca(tokens[1]);
                                camioneta.setModelo(tokens[2]);
                                camioneta.setTipoMotor(tokens[3]);
                                camioneta.setAnio(Integer.parseInt(tokens[4]));
                                camioneta.setRecorrido(Double.parseDouble(tokens[5]));
                                camioneta.setColor(tokens[6]);
                                camioneta.setCombustible(tokens[7]);
                                camioneta.setVidrios(tokens[8]);
                                camioneta.setTransmicion(tokens[9]);
                                camioneta.setTraccion(tokens[10]);
                                camioneta.setPrecio(Double.parseDouble(tokens[11]));
                                camioneta.setVendedor(v);
                                vehiculosT.add(camioneta);
                                break;
                            }
                        }
                    }
                    default -> {
                        System.out.println("ola");
                        break;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        v.setVehiculos(vehiculosT);

        do {
            if (condicion == false) {
                System.out.println("Ingrese una nueva placa:");
                placa = sc.nextLine();
                condicion = validarPlaca(placa);
            }

        } while (condicion == false);

        if (validarPlaca(placa) == true) {

            System.out.println(v.getVehiculos().size());
            for (Vehiculo vehiculo : v.getVehiculos()) {
                if (vehiculo.getPlaca().equals(placa)) {
                    System.out.println(vehiculo.getModelo());
                    System.out.println("Precio: " + vehiculo.getPrecio());

                }
            }
            //
            ArrayList<Oferta> ofertasVehiculos = new ArrayList<>();

            String nombComprador;
            String apeComprador = "";
            String correoComprador;
            double ofertaComprador = 0.;
            

            Comprador compOferta = new Comprador(null, null, null, null, null);
            Oferta oferta = new Oferta(null, null, 0.);
            try (Scanner reader = new Scanner((new File("ofertas.txt")))) {
                while (reader.hasNextLine()) {
                    String linea = reader.nextLine();
                    String[] tokens = linea.split("\\|");
                    nombComprador = tokens[0];
                    apeComprador = tokens[1];
                    correoComprador = tokens[2];
                    ofertaComprador = Double.parseDouble(tokens[4]);
                    if (tokens[3].equals(v.getVehiculos().get(0).getPlaca())) {
                        compOferta.setNombre(nombComprador);
                        compOferta.setApellidos(apeComprador);
                        compOferta.setCorreoElectronico(correoComprador);
                        oferta.setVehiculo(v.getVehiculos().get(0));
                        oferta.setComprador(compOferta);
                        oferta.setPrecio(ofertaComprador);
                        ofertasVehiculos.add(oferta);
                    }

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            Scanner es = new Scanner(System.in);
            System.out.println("Se han realizado " + ofertasVehiculos.size() + " ofertas");
            int i = 0;
            int opcion;
            int cerrarWhile = 0;

            while (cerrarWhile == 0) {
                if (i < ofertasVehiculos.size()) {

                    System.out.println("Correo: " + ofertasVehiculos.get(i).getComprador().getCorreoElectronico());
                    System.out.println("Precio ofertado: " + ofertasVehiculos.get(i).getPrecio());

                    if ((i < (ofertasVehiculos.size() - 1)) && (i != 0)) { //limitando la opcion de avanzar solo hasta el penultimo item

                        System.out.println("1.Siguiente opcion");
                        System.out.println("2.Anterior opcion");
                        System.out.println("3.Aceptar oferta");

                        do {
                            opcion = es.nextInt();
                            switch (opcion) {
                                case 1:
                                    i += 1;
                                    break;
                                case 2:
                                    i -= 1;
                                    break;
                                case 3:
                                    v.aceptarOferta(ofertasVehiculos.get(i).getVehiculo(), ofertasVehiculos.get(i));
                                    opcion = 15;
                                    cerrarWhile = 1;
                                    break;
                                default:
                                    System.out.println("Ingresa una opcion valida");
                                    opcion = 10;
                                    break;
                            }
                        } while (opcion == 10);

                    } else if (i == 0) { //limitando la opcion de solo avanzar en el primer item

                        System.out.println("1.Siguiente opcion");

                        System.out.println("2.Aceptar oferta");

                        do {
                            opcion = es.nextInt();
                            switch (opcion) {
                                case 1:
                                    i += 1;
                                    break;
                                case 2:
                                    v.aceptarOferta(ofertasVehiculos.get(i).getVehiculo(), ofertasVehiculos.get(i));
                                    opcion = 15;
                                    cerrarWhile = 1;
                                    break;
                                default:
                                    System.out.println("Ingresa una opcion valida");
                                    opcion = 10;
                                    break;
                            }
                        } while (opcion == 10);

                    } else if (i == ofertasVehiculos.size() - 1) { //limita a que solo retroceda en el ultimo item
                        System.out.println("1.Anterior opcion");

                        System.out.println("2.Realizar oferta");

                        do {
                            opcion = es.nextInt();
                            switch (opcion) {
                                case 1:
                                    i -= 1;
                                    break;
                                case 2:
                                    v.aceptarOferta(ofertasVehiculos.get(i).getVehiculo(), ofertasVehiculos.get(i));
                                    opcion = 15;
                                    cerrarWhile = 1;
                                    break;
                                default:
                                    System.out.println("Ingresa una opcion valida");
                                    opcion = 10;
                                    break;
                            }
                        } while (opcion == 10);

                    }
                }
            }

        }
    }
}
