import java.util.Scanner;
import java.util.InputMismatchException;
public class ProyectoHawkings{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        final double MAX_ENERGY = 200.0f;
        final double LIMITE_RIESGO =150.0f;
        String operadorActual;
        double sensorMedido = 0d;
        int codigoRiesgo = 0;
        int porcentajeEstado;
        boolean portalAbiertos = false;
        int opcionMenu = 0, i = 0;
        System.out.print("Bienvenido, introduce el usuario: ");
        operadorActual = sc.nextLine();
        do{
            mostrarEncabezado(operadorActual);
            System.out.print("Introduce una opcion: ");
            opcionMenu = sc.nextInt();
            
            switch (opcionMenu) {
                case 1:
                    sensorMedido = leerLecturaSegura(sc, "Introduce el valor leeido: ");
                    codigoRiesgo = evaluarNivelRiesgo(sensorMedido, LIMITE_RIESGO);
                    porcentajeEstado = calcularPorcentajeTruncado(sensorMedido, MAX_ENERGY);
                    portalAbiertos = ejecutarProtocoloAccion(codigoRiesgo);
                    System.out.println("Lectura del sensor: " + sensorMedido);
                    System.out.println("Codigo de riesgo: " + codigoRiesgo);
                    System.out.println("Porcentaje truncado: " + porcentajeEstado);
                    System.out.println("Portal abierto: " + portalAbiertos);
                    break;
                case 2:
                    simularMuestreoContinuo(sc);
                    break;
                case 0:
                    System.out.println("Adios.");
                    break;
                default:
                    System.out.println("ERROR, Opcion incorrecta, intentalo de nuevo.");
                    break;
            }
        }while(opcionMenu != 0);
    }
    public static void mostrarEncabezado(String usr){
        System.out.printf("Menu\nBienvenido " + usr + "\n--SISTEMA HAWKINS\n1. Inspeccionar sensor actual.\n2. Iniciar muestreo continuo.\n0. Salir de la consola.\n");
    }
    public static double leerLecturaSegura(Scanner sc ,String msg){
        boolean type = false;
        double lectura = 0d;
        do{
        try{
        System.out.print(msg);
        lectura = sc.nextDouble();
        type = true;
        
        }
        catch(InputMismatchException e){
            System.out.println(e.getMessage());
            System.out.println("Ese tipo de dato no es correcto, intentalo de nuevo.");
            sc.next();

        }
        }while(!type);
        type = false;
        return lectura;
    }
    public static int evaluarNivelRiesgo(double lectura, double lmt_rg){
        int codigoRiesgo = 0;
        if(lectura <50){
            codigoRiesgo = 1;
        }
        else if(lectura>= 50 && lectura < lmt_rg){
            codigoRiesgo = 2;
        }
        else{
            codigoRiesgo = 3;
        }
        System.out.println(codigoRiesgo == 1 ? "Seguro": codigoRiesgo == 2 ? "Alerta.":"Critico");
        return codigoRiesgo;

    }
    public static int calcularPorcentajeTruncado(double lectura, double MAX_ENERGY){
        double porcentaje = (lectura/MAX_ENERGY)*100;
        int porcentajeEstado = (int)porcentaje;
        return porcentajeEstado;
    }
    public static boolean ejecutarProtocoloAccion(int codigoRiesgo){
       switch (codigoRiesgo) {
        case 1:
            System.out.println("Modo estable.");
            return false;

        case 2:
            System.out.println("Anomalia detectada.");
            return false;

        case 3:
            System.out.println("¡Alerta Maxima!");
            return true;
       
        default:
            return false;
       } 
    }
    public static void simularMuestreoContinuo(Scanner scr){
        double lectura = 0d;
        int i= 0;
        double media = 0d;
        do{
        lectura = leerLecturaSegura(scr, "Introduce la lectura: ");
        if(lectura < 0){
            break;
        }
        else if(lectura == 0){
            continue;
        }
        else if(lectura > 150){
            System.out.println("CUIDADO, HAS SUPERADO EL UMBRAL DE SEGURIDAD.");
        }
        i++;
        media +=lectura;        
    }while(lectura>0);
    i++;
        System.out.println("Has introducido "+ i +" valores validos.");
        System.out.println("La media de los valores introducidos es: " + (media/i));
    }
}