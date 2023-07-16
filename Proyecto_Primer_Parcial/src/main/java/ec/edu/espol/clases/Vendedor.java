/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.clases;

import static ec.edu.espol.clases.Comprador.inicioSesion;
import static ec.edu.espol.clases.Hash.getSHA;
import static ec.edu.espol.clases.Hash.toHexString;
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

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public void menuVendedor() throws NoSuchAlgorithmException {
        System.out.println("1. Registrar nuevo Vendedor");
        System.out.println("2. Registrar un nuevo Vehiculo");
        System.out.println("3. Aceptar oferta");
        System.out.println("4. Regresar");

        Scanner entrada = new Scanner(System.in);
        int respuesta = entrada.nextInt();

        switch (respuesta) {
            case 1:
                registrarVendedor();
                break;

            case 2:

                break;

            case 3:
                revisarOfertas();
                break;

            case 4:

                break;
        }

    }

    public Vendedor registrarVendedor() throws NoSuchAlgorithmException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingresar nombres: ");
        String nombres = sc.next();

        System.out.println("Ingresar apellidos: ");
        String apellidos = sc.next();

        System.out.println("Ingresar organización: ");
        String organizacion = sc.next();

        System.out.println("IngresarCorreoElectronico: ");
        String correo = sc.next();

        //Validar Correo
        System.out.println("Ingresar Clave: ");
        String clave = sc.next();
        String claveHash = toHexString(getSHA(clave));

        Vendedor vendedor = new Vendedor(nombres, apellidos, organizacion, correo, claveHash);
        return vendedor;
    }

    public Vehiculo validarPlaca() {
        Vehiculo v_1;
        System.out.println("Ingrese la placa:");
        Scanner sn = new Scanner(System.in);
        String placa = sn.nextLine();
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equals(placa)) {
                v_1 = v;
                System.out.println(v.getModelo() + "precio:" + v.getPrecio());
                System.out.println("Se han realizado:" + v.getOfertas().size() + "Ofertas");
                return v_1;
            }
        }
        return null;
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

    public void revisarOfertas() {
        Scanner sn = new Scanner(System.in);
        System.out.println("Ingrese su correo electronico:");
        String correo_elec = sn.nextLine();
        System.out.println("Ingrese su contraseña:");
        String contra = sn.nextLine();
        boolean condicion = inicioSesion(correo_elec, contra);
        if (condicion) {
            Vehiculo vehiculo_1 = validarPlaca();
            int opcion;
            do {
                int contador=0;
                Oferta oferta_1 = vehiculo_1.mostrarOferta(contador);
                        System.out.println("1. Siguiente oferta");
                        System.out.println("2. Aceptar Oferta");
                opcion = sn.nextInt();
                sn.nextLine();
                switch(opcion){
                    case 1:
                        int i = vehiculo_1.siguienteOferta(contador);
                        contador=i;
                        vehiculo_1.mostrarOferta(contador);
                        break;
                    case 2:
                        aceptarOferta(vehiculo_1, oferta_1);
                        break;
                }
            } 
        }
    }
}
