
package ec.edu.espol.clases;

import static ec.edu.espol.clases.Hash.getSHA;
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
    
    public Comprador(String correoElectronico, String clave){
        super(null, null, null,correoElectronico, clave);
        this.ofertas= null;
    }

    public void setOfertas(ArrayList<Oferta> ofertas) {
        this.ofertas = ofertas;
    }
    
    
    public static Comprador registrarComprador(){
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
        String contraHash= null;
        try
        {
            contraHash = Hash.toHexString(getSHA(contrasena));
            
        }
        // For specifying wrong message digest algorithms
        catch (Exception e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }
    
        
        Comprador Ncomprador= new Comprador(null,nombres, 
                                apellidos,org,
                                correo,contraHash );      
        
        do{
            if (validarCorreo(Ncomprador) == false){
                try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("compradores.txt"),true)))
                {
                    pw.println(nombres+"|"+apellidos+"|"+org+"|"+correo+"|"+contraHash);
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
    
    
    public static ArrayList<Vehiculo> buscarVehiculo(String tipo, String recorrido, String ano, String precio){
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
                    
                if(((tamano == 11) && (tipovehiculo == 11)) || (tipovehiculo ==1)){ //#de atributos que tendran los autos
                   if((Integer.parseInt(recorridoRango[0]) <= Integer.parseInt(tokens[5]))
                        || (Integer.parseInt(recorridoRango[1]) >= Integer.parseInt(tokens[5]))){
                        if((Integer.parseInt(anoRango[0]) <= Integer.parseInt(tokens[4]))
                        || (Integer.parseInt(anoRango[1]) >= Integer.parseInt(tokens[4]))){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Auto vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);
                            }
                            else if(precio.isBlank()){
                                Auto vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);                                                     
                            }
                   }
                        else if(ano.isBlank()){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Auto vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);
                            }
                            }
                            else if(precio.isBlank()){
                                Auto vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);
                            }
                   }
                   else if (recorrido.isBlank()){
                       if((Integer.parseInt(anoRango[0]) <= Integer.parseInt(tokens[4]))
                        || (Integer.parseInt(anoRango[1]) >= Integer.parseInt(tokens[4]))){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Auto vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);
                            }
                            else if(precio.isBlank()){
                                Auto vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);                                                    
                            }
                   }
                        else if(ano.isBlank()){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Auto vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);
                            }
                            }
                            else if(precio.isBlank()){
                                Auto vehiculo = new Auto(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],tokens[7],tokens[8],tokens[9],Double.parseDouble(tokens[10]));
                                vehiculos.add(vehiculo);
                            }
                       
                   }
                }
                      
                if(((tamano == 9) && (tipovehiculo == 9)) || (tipovehiculo ==1)){ //#de atributos que tendran las motos
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
                if(((tamano == 12) && (tipovehiculo == 12)|| (tipovehiculo ==1))){ //#de atributos que tendran las camionetas
                   if((Integer.parseInt(recorridoRango[0]) <= Integer.parseInt(tokens[5]))
                        || (Integer.parseInt(recorridoRango[1]) >= Integer.parseInt(tokens[5]))){
                        if((Integer.parseInt(anoRango[0]) <= Integer.parseInt(tokens[4]))
                        || (Integer.parseInt(anoRango[1]) >= Integer.parseInt(tokens[4]))){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Camioneta vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);
                            }
                            else if(precio.isBlank()){
                                Camioneta vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);                                                    
                            }
                   }
                        else if(ano.isBlank()){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Camioneta vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);
                            }
                            }
                            else if(precio.isBlank()){
                                Camioneta vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
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
                                Camioneta vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);
                            }
                            else if(precio.isBlank()){
                                Camioneta vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);                                                   
                            }
                   }
                        else if(ano.isBlank()){
                            if((Integer.parseInt(precioRango[0]) <= Integer.parseInt(tokens[10]))
                            || (Integer.parseInt(precioRango[1]) >= Integer.parseInt(tokens[10]))){
                                Camioneta vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
                                       Integer.parseInt(tokens[4]),Double.parseDouble(tokens[5]),tokens[6],
                                        tokens[7],tokens[8],tokens[9],tokens[10],Double.parseDouble(tokens[11]));
                                vehiculos.add(vehiculo);
                            }
                            }
                            else if(precio.isBlank()){
                                Camioneta vehiculo = new Camioneta(tokens[0],tokens[1],tokens[2],tokens[3],
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
        
    
    public static void hacerOferta(){
//        
         
         Scanner sc = new Scanner(System.in);
         String correo = sc.next();
         String contrasena = sc.next();
         ArrayList<Oferta> ofertasVehiculo= new ArrayList<>();
         
         inicioSesion(correo, contrasena); //Siempre retornara true por las condiciones del metodo

         Comprador comprador = new Comprador(correo, contrasena);        
         
         System.out.println("Ingrese tipo de vehiculo");
         String tipo = sc.next();
         System.out.println("Ingrese rango de recorrido, ejem.: 5000-10000");
         String recorrido = sc.next();
         System.out.println("Ingrese rango de año, ejem.: 1970-2000");
         String ano = sc.next();
         System.out.println("Ingrese rango de precio");
         String precio = sc.next();
         ArrayList<Vehiculo> vehiculo= buscarVehiculo(tipo,recorrido,ano,precio);
         int opcion ;
         int i=0;
         while( i<vehiculo.size()){
             if (vehiculo.get(i) instanceof Auto){ //mostrar toda la informacion del vehiculo si es un auto
                 System.out.println(("placa"+vehiculo.get(i).getPlaca()));
                 System.out.println(("marca"+vehiculo.get(i).getMarca()));
                 System.out.println(("modelo"+vehiculo.get(i).getModelo()));
                 System.out.println(("tipo de motor"+vehiculo.get(i).getTipoMotor()));
                 System.out.println(("año de fabricación"+vehiculo.get(i).getAnio()));
                 System.out.println(("kilometraje"+vehiculo.get(i).getRecorrido()));
                 System.out.println(("color"+vehiculo.get(i).getColor()));
                 System.out.println(("tipo de combustible"+vehiculo.get(i).getTipoCombustible()));
                 System.out.println(("vidrios"+vehiculo.get(i).getVidrios()));
                 System.out.println(("transmision"+vehiculo.get(i).getTransmision()));
                 System.out.println(("precio"+vehiculo.get(i).getPrecio()));
                 System.out.println("");
                 if((i < (vehiculo.size()-1)) && (i != 0)){ //limitando la opcion de avanzar solo hasta el penultimo item
                     
                         System.out.println("1.Siguiente opcion");                 
                         System.out.println("2.Anterior opcion");
                         System.out.println("3.Realizar oferta");
                     
                     do{
                         opcion = sc.nextInt();
                     switch(opcion){
                        case 1:
                            i+=1;
                            break;
                        case 2:
                            i-=1;
                            break;
                        case 3:
                            System.out.println("Ingrese su oferta");
                            double oferton = sc.nextDouble();
                            Oferta oferta = new Oferta(comprador, vehiculo.get(i),oferton);
                            ofertasVehiculo.add(oferta);
                            comprador.setOfertas(ofertasVehiculo);
                            try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("ofertas.txt"),true)))
                            {
                                pw.println(comprador.getNombre()+"|"+ comprador.getApellidos()+"|"+
                                    comprador.getCorreoElectronico()+"|"+ (oferta.getVehiculo()).getPlaca() + oferta.getPrecio());
                            }
                            catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                            opcion = 15; //#para cerrar el while
                            i = 999999;//# para que se acabe el programa y realice la oferta
                            break;
                        default:
                            System.out.println("Ingresa una opcion valida");
                            opcion=10;
                     }
                     } while(opcion == 10);
                     
             }
                 else if(i == 0){ //limitando la opcion de solo avanzar en el primer item
                     
                         System.out.println("1.Siguiente opcion");                 
                         
                         System.out.println("2.Realizar oferta");
                     
                     do{
                         opcion = sc.nextInt();
                     switch(opcion){
                        case 1:
                            i+=1;
                            break;
                        case 2:
                            System.out.println("Ingrese su oferta");
                            double oferton = sc.nextDouble();
                            Oferta oferta = new Oferta(comprador, vehiculo.get(i),oferton);
                            ofertasVehiculo.add(oferta);
                            comprador.setOfertas(ofertasVehiculo);
                            try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("ofertas.txt"),true)))
                            {
                                pw.println(comprador.getNombre()+"|"+ comprador.getApellidos()+"|"+
                                    comprador.getCorreoElectronico()+"|"+ (oferta.getVehiculo()).getPlaca() + oferta.getPrecio());
                            }
                            catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                            opcion = 15;
                            i=999999;//# para que se acabe el programa y realice la oferta
                            
                            break;
                        default:
                            System.out.println("Ingresa una opcion valida");
                            opcion=10;
                     }
                     } while(opcion == 10);
                     
             }
                 else if(i == vehiculo.size()-1){ //limita a que solo retroceda en el ultimo item
                     System.out.println("1.Anterior opcion");                 
                         
                     System.out.println("2.Realizar oferta");
                     
                     do{
                         opcion = sc.nextInt();
                     switch(opcion){
                        case 1:
                            i-=1;
                            break;
                        case 2:
                            System.out.println("Ingrese su oferta");
                            double oferton = sc.nextDouble();
                            Oferta oferta = new Oferta(comprador, vehiculo.get(i),oferton);
                            ofertasVehiculo.add(oferta);
                            comprador.setOfertas(ofertasVehiculo);
                            try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("ofertas.txt"),true)))
                            {
                                pw.println(comprador.getNombre()+"|"+ comprador.getApellidos()+"|"+
                                    comprador.getCorreoElectronico()+"|"+ (oferta.getVehiculo()).getPlaca() + oferta.getPrecio());
                            }
                            catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                            opcion = 15;
                            i=999999;//# para que se acabe el programa y realice la oferta
                            
                            break;
                        default:
                            System.out.println("Ingresa una opcion valida");
                            opcion=10;
                     }
                     } while(opcion == 10);
                     
                 }
             }
             else if (vehiculo.get(i) instanceof Camioneta){ //mostrar toda la informacion del vehiculo si es una camioneta
                 System.out.println(("placa"+vehiculo.get(i).getPlaca()));
                 System.out.println(("marca"+vehiculo.get(i).getMarca()));
                 System.out.println(("modelo"+vehiculo.get(i).getModelo()));
                 System.out.println(("tipo de motor"+vehiculo.get(i).getTipoMotor()));
                 System.out.println(("año de fabricación"+vehiculo.get(i).getAnio()));
                 System.out.println(("kilometraje"+vehiculo.get(i).getRecorrido()));
                 System.out.println(("color"+vehiculo.get(i).getColor()));
                 System.out.println(("tipo de combustible"+vehiculo.get(i).getTipoCombustible()));
                 System.out.println(("vidrios"+vehiculo.get(i).getVidrios()));
                 System.out.println(("transmision"+vehiculo.get(i).getTransmision()));
                 System.out.println("traccion"+ vehiculo.get(i).getTraccion());
                 System.out.println(("precio"+vehiculo.get(i).getPrecio()));
                 System.out.println("");
                 if(i < (vehiculo.size()-1) && (i != 0)){ //limitando la opcion de avanzar solo hasta el penultimo item
                     
                         System.out.println("1.Siguiente opcion");                 
                         System.out.println("2.Anterior opcion");
                         System.out.println("3.Realizar oferta");
                     
                     do{
                         opcion = sc.nextInt();
                     switch(opcion){
                        case 1:
                            i+=1;
                            break;
                        case 2:
                            i-=1;
                            break;
                        case 3:
                            System.out.println("Ingrese su oferta");
                            double oferton = sc.nextDouble();
                            Oferta oferta = new Oferta(comprador, vehiculo.get(i),oferton);
                            ofertasVehiculo.add(oferta);
                            comprador.setOfertas(ofertasVehiculo);
                            try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("ofertas.txt"),true)))
                            {
                                pw.println(comprador.getNombre()+"|"+ comprador.getApellidos()+"|"+
                                    comprador.getCorreoElectronico()+"|"+ (oferta.getVehiculo()).getPlaca() + oferta.getPrecio());
                            }
                            catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                            opcion = 15;
                            i = 999999; //# para que se acabe el programa y realice la oferta
                            break;
                            
                        default:
                            System.out.println("Ingresa una opcion valida");
                            opcion=10;
                     }
                     } while(opcion == 10);
                     
             }
                 else if(i == 0){ //limitando la opcion de solo avanzar en el primer item
                     
                         System.out.println("1.Siguiente opcion");                 
                         
                         System.out.println("2.Realizar oferta");
                     
                     do{
                         opcion = sc.nextInt();
                     switch(opcion){
                        case 1:
                            i+=1;
                            break;
                        case 2:
                            System.out.println("Ingrese su oferta");
                            double oferton = sc.nextDouble();
                            Oferta oferta = new Oferta(comprador, vehiculo.get(i),oferton);
                            ofertasVehiculo.add(oferta);
                            comprador.setOfertas(ofertasVehiculo);
                            try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("ofertas.txt"),true)))
                            {
                                pw.println(comprador.getNombre()+"|"+ comprador.getApellidos()+"|"+
                                    comprador.getCorreoElectronico()+"|"+ (oferta.getVehiculo()).getPlaca() + oferta.getPrecio());
                            }
                            catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                            opcion = 15;
                            i=999999;//# para que se acabe el programa y realice la oferta
                            
                            break;
                        default:
                            System.out.println("Ingresa una opcion valida");
                            opcion=10;
                     }
                     } while(opcion == 10);
                     
             }
                 else if(i == vehiculo.size()-1){ //limita a que solo retroceda en el ultimo item
                     System.out.println("1.Anterior opcion");                 
                         
                     System.out.println("2.Realizar oferta");
                     
                     do{
                         opcion = sc.nextInt();
                     switch(opcion){
                        case 1:
                            i-=1;
                            break;
                        case 2:
                            System.out.println("Ingrese su oferta");
                            double oferton = sc.nextDouble();
                            Oferta oferta = new Oferta(comprador, vehiculo.get(i),oferton);
                            ofertasVehiculo.add(oferta);
                            comprador.setOfertas(ofertasVehiculo);
                            try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("ofertas.txt"),true)))
                            {
                                pw.println(comprador.getNombre()+"|"+ comprador.getApellidos()+"|"+
                                    comprador.getCorreoElectronico()+"|"+ (oferta.getVehiculo()).getPlaca() + oferta.getPrecio());
                            }
                            catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                            opcion = 15;
                            i=999999;//# para que se acabe el programa y realice la oferta
                            
                            break;
                        default:
                            System.out.println("Ingresa una opcion valida");
                            opcion=10;
                     }
                     } while(opcion == 10);
                     
                 }
             }
             if (vehiculo.get(i) instanceof Vehiculo){ //mostrar toda la informacion del vehiculo si es una moto
                 System.out.println(("placa"+vehiculo.get(i).getPlaca()));
                 System.out.println(("marca"+vehiculo.get(i).getMarca()));
                 System.out.println(("modelo"+vehiculo.get(i).getModelo()));
                 System.out.println(("tipo de motor"+vehiculo.get(i).getTipoMotor()));
                 System.out.println(("año de fabricación"+vehiculo.get(i).getAnio()));
                 System.out.println(("kilometraje"+vehiculo.get(i).getRecorrido()));
                 System.out.println(("color"+vehiculo.get(i).getColor()));
                 System.out.println(("tipo de combustible"+vehiculo.get(i).getTipoCombustible()));
                 System.out.println(("precio"+vehiculo.get(i).getPrecio()));
                 System.out.println("");
                 if(i < (vehiculo.size()-1)&& (i != 0)){ //limitando la opcion de avanzar solo hasta el penultimo item
                     
                         System.out.println("1.Siguiente opcion");                 
                         System.out.println("2.Anterior opcion");
                         System.out.println("3.Realizar oferta");
                     
                     do{
                         opcion = sc.nextInt();
                     switch(opcion){
                        case 1:
                            i+=1;
                            break;
                        case 2:
                            i-=1;
                            break;
                        case 3:
                            System.out.println("Ingrese su oferta");
                            double oferton = sc.nextDouble();
                            Oferta oferta = new Oferta(comprador, vehiculo.get(i),oferton);
                            ofertasVehiculo.add(oferta);
                            comprador.setOfertas(ofertasVehiculo);
                            try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("ofertas.txt"),true)))
                            {
                                pw.println(comprador.getNombre()+"|"+ comprador.getApellidos()+"|"+
                                    comprador.getCorreoElectronico()+"|"+ (oferta.getVehiculo()).getPlaca() + oferta.getPrecio());
                            }
                            catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                            opcion = 15;
                            i = 999999;
                            break;
                        default:
                            System.out.println("Ingresa una opcion valida");
                            opcion=10;
                     }
                     } while(opcion == 10);
                     
             }
                 else if(i == 0){ //limitando la opcion de solo avanzar en el primer item
                     
                         System.out.println("1.Siguiente opcion");                 
                         
                         System.out.println("2.Realizar oferta");
                     
                     do{
                         opcion = sc.nextInt();
                     switch(opcion){
                        case 1:
                            i+=1;
                            break;
                        case 2:
                            System.out.println("Ingrese su oferta");
                            double oferton = sc.nextDouble();
                            Oferta oferta = new Oferta(comprador, vehiculo.get(i),oferton);
                            ofertasVehiculo.add(oferta);
                            comprador.setOfertas(ofertasVehiculo);
                            try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("ofertas.txt"),true)))
                            {
                                pw.println(comprador.getNombre()+"|"+ comprador.getApellidos()+"|"+
                                    comprador.getCorreoElectronico()+"|"+ (oferta.getVehiculo()).getPlaca() + oferta.getPrecio());
                            }
                            catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                            opcion = 15;
                            i=999999;//# para que se acabe el programa y realice la oferta
                            
                            break;
                        default:
                            System.out.println("Ingresa una opcion valida");
                            opcion=10;
                     }
                     } while(opcion == 10);
                     
             }
                 else if(i == vehiculo.size()-1){ //limita a que solo retroceda en el ultimo item
                     System.out.println("1.Anterior opcion");                 
                         
                     System.out.println("2.Realizar oferta");
                     
                     do{
                         opcion = sc.nextInt();
                     switch(opcion){
                        case 1:
                            i-=1;
                            break;
                        case 2:
                            System.out.println("Ingrese su oferta");
                            double oferton = sc.nextDouble();
                            Oferta oferta = new Oferta(comprador, vehiculo.get(i),oferton);
                            ofertasVehiculo.add(oferta);
                            comprador.setOfertas(ofertasVehiculo);
                            try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("ofertas.txt"),true)))
                            {
                                pw.println(comprador.getNombre()+"|"+ comprador.getApellidos()+"|"+
                                    comprador.getCorreoElectronico()+"|"+ (oferta.getVehiculo()).getPlaca() + oferta.getPrecio());
                            }
                            catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                            opcion = 15;
                            i=999999;//# para que se acabe el programa y realice la oferta
                            
                            break;
                        default:
                            System.out.println("Ingresa una opcion valida");
                            opcion=10;
                     }
                     } while(opcion == 10);
                     
                 }
             }
         }
    }
    
    public static boolean validarCorreo(Comprador p){
        try(Scanner sc = new Scanner(new File("compradores.txt")) ){
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
    
    public static boolean validarContrasena(Comprador p){
        try(Scanner sc = new Scanner(new File("compradores.txt")) ){
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
    
    public static void opcionesComprador(){
        Scanner sc = new Scanner(System.in);
        boolean salir= false;
        int opcion;
        
        while(!salir){
            System.out.println("1.Registrar un nuevo comprador");
            System.out.println("2.Ofertar por un vehiculo");
            System.out.println("3.Salir");
            
            try{
                System.out.println("Eliga que desea hacer");
                opcion = sc.nextInt();
                switch(opcion){
                    case 1:
                        registrarComprador();
                        break;
                    case 2:
                        
                        break;
                    case 3:
                        salir= true;
                    default:
                        System.out.println("Eliga una opcion valida");
                    
                }              
            }catch(Exception e){
                System.out.println("Debes escribir un numero");
                sc.next();
            }
        }
        System.out.println("Regresando al menu anterior");
    }
    public static boolean inicioSesion(String correo, String contrasena) {
        Scanner sc = new Scanner(System.in);
        
            System.out.println("Ingrese correo");
            System.out.println("Ingrese contraseña");
            correo= sc.next();
            contrasena= sc.next();
            Comprador comprador= new Comprador(correo, contrasena);
            while (validarCorreo(comprador) == false){
                System.out.println("Correo invalido, ingrese uno existente");
                correo= sc.next();
                comprador.setCorreoElectronico(correo);
            }
            
            while(validarContrasena(comprador)== false){
                System.out.println("Contrasena invalida, intentelo de nuevo");
                contrasena = sc.next();
                comprador.setClave(contrasena);
            }
            
     return ((validarCorreo(comprador) == true)&& (validarContrasena(comprador)==true));
    }
}

