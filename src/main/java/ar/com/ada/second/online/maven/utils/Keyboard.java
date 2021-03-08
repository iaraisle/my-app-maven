package ar.com.ada.second.online.maven.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Keyboard {
    // 1) crear un attr privado static de la instancia que queremos que sea unica
    private static Scanner scanner;

    // 2) el constructor debe ser privado.
    private Keyboard() {
    }

    // 3) crear un metodo statico y publico llamado getInstance()
    public static Scanner getInstance() {
        if (scanner == null)
            scanner = new Scanner(System.in);

        return scanner;
    }

    // pressEnterKeyToContinue
    public static void pressEnterKeyToContinue() {
        System.out.println("Presione la tecla Enter para continuar...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    // invalidData
    public static void invalidData() {
        System.out.println("ERROR :: tipo de dato invalido, intente de nuevo");
    }

    public static String getInputAlphanumeric() {
        Scanner keyboard = getInstance();
        boolean aux = true;
        String txt = null;

        while (aux) {
            try {
                System.out.println("? ");
                txt = keyboard.nextLine().trim();
                while (!txt.isEmpty() && !txt.matches("^[0-9A-Za-záéíóúÁÉÍÓÚñÑ\\.\\s]+$")) {
                    invalidData();
                    txt = keyboard.nextLine().trim();
                }
                aux = false;
            } catch (InputMismatchException e) {
                invalidData();
                keyboard.next();
            }
        }

        return txt;
    }


    // getInputString
    public static String getInputString() {
        Scanner keyboard = getInstance();
        boolean aux = true;
        String txt = null;

        while (aux) {
            try {
                System.out.println("? ");
                txt = keyboard.nextLine().trim();
                while (!txt.isEmpty() && !txt.matches("^[A-Za-záéíóúÁÉÍÓÚñÑ\\s]+$")) {
                    invalidData();
                    txt = keyboard.nextLine().trim();
                }
                aux = false;
            } catch (InputMismatchException e) {
                invalidData();
                keyboard.next();
            }
        }

        return txt;
    }

    public static Integer getInputInteger() {
        Scanner keyboard = getInstance();
        boolean aux = true;
        String txt = null;

        while (aux) {
            try {
                System.out.println("? ");
                txt = keyboard.nextLine().trim();
                while (!txt.isEmpty() && !txt.matches("^[0-9]+$")) {
                    invalidData();
                    txt = keyboard.nextLine().trim();
                }
                aux = false;
            } catch (InputMismatchException e) {
                invalidData();
                keyboard.next();
            }
        }

        // "123" => 123
        return Integer.parseInt(txt);
    }

    public static Double getInputDouble() {
        Scanner keyboard = getInstance();
        boolean aux = true;
        String txt = null;

        while (aux) {
            try {
                System.out.println("? ");
                txt = keyboard.nextLine().trim();
                while (!txt.isEmpty() && !txt.matches("-?[0-9]{1,13}(\\.[0-9]*)?")) {
                    invalidData();
                    txt = keyboard.nextLine().trim();
                }
                aux = false;
            } catch (InputMismatchException e) {
                invalidData();
                keyboard.next();
            }
        }

        // "123.123" => 123.123
        return Double.parseDouble(txt);
    }
    public static String getInputEmail() {
        Scanner keyboard = getInstance();
        boolean aux = true;
        String txt = null;
        while (aux) {
            try {
                System.out.print("? ");
                txt = keyboard.nextLine().trim();
                while (!txt.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$") && !txt.isEmpty()) {
                    invalidData();
                    txt = keyboard.nextLine();
                }
                aux = false;
            } catch (InputMismatchException e) {
                invalidData();
                keyboard.next();
            }
        }
        return txt;
    }
}
