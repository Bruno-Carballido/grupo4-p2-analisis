import entities.Tweet;
import entities.User;
import uy.edu.um.prog2.adt.Hash;
import uy.edu.um.prog2.adt.HashImpl;
import uy.edu.um.prog2.adt.LinkedList;
import uy.edu.um.prog2.adt.LinkedListImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern p = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Hash<String, User> pilotos = new HashImpl<>(25);
        Hash<String, User> usuarios = new HashImpl<>(30000);
        LinkedList<Tweet> tweets = new LinkedListImpl<>();

        try {
            BufferedReader drivers = new BufferedReader(new FileReader("obligatorio2023/drivers.txt"));
            String line = null;

            // Carga de usuarios ------------
            long idUser = 0;
            while ((line = drivers.readLine()) != null) {
                User user = new User(idUser, line);
                pilotos.put(line, user);
                idUser++;
            }
            // Fin carga de usuarios ------------

            BufferedReader input = new BufferedReader(new FileReader("obligatorio2023/f1_dataset_test.csv"));
            String[] rows = input.readLine().split(",");
            line = null;
            int cantColumnas = rows.length;
            int idUsuario = 0;

            StringBuilder filaIncompleta = new StringBuilder();
            while ((line = input.readLine()) != null) {// leo hasta salto de línea
                String new_line = filaIncompleta + line;
                String[] fields = p.split(new_line, -1); // parseo campos por comillas
                boolean isComillasPar = cantComillasPar(new_line);
                if (fields.length == cantColumnas && isComillasPar) {
                    filaIncompleta.setLength(0);
                    User u;
                    try {
                        u = usuarios.get(fields[1]);
                    } catch (Exception ex) {
                        u = new User(idUsuario, fields[1]);
                        usuarios.put(fields[1], u);
                        idUsuario++;
                    }
                    tweets.add(new Tweet(Long.parseLong(fields[0]), fields[10], fields[12], Boolean.parseBoolean(fields[13]), u));
                    //System.out.println(fields[0]);
                } else if (fields.length > cantColumnas && isComillasPar) {
                    filaIncompleta.setLength(0);
                } else {
                    filaIncompleta.append(line);
                }
            }
            input.close();

            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;

            System.out.println("--------------------");
            System.out.printf("%s %s%n", "Cantidad de Tweets:", tweets.size());
            System.out.printf("%s %s%n", "Cantidad de usuarios:", idUsuario);
            System.out.printf("%s %s%s%n", "Tiempo de ejecución:", timeElapsed, "ms");
            System.out.println("--------------------");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        menu();
    }

    private static boolean cantComillasPar(String line) {
        return (line.length() - line.replace("\"", "").length()) % 2 == 0;
    }

    public static void menu() {
        System.out.println("Menú principal");
        System.out.println("Seleccione la opción del menú: ");
        System.out.println("    1. Listar los 10 pilotos activos en la temporada 2023 más mencionados en los tweets en un mes");
        System.out.println("    2. Top 15 usuarios con más tweets. ");
        System.out.println("    3. Cantidad de hashtags distintos para un día dado");
        System.out.println("    4. Hashtag más usado para un día dado, sin tener en cuenta #f1");
        System.out.println("    5. Top 7 cuentas con más favoritos");
        System.out.println("    6. Cantidad de tweets con una palabra o frase específicos");
        System.out.println("    7. Finalizar programa");

        System.out.print("Ingrese su opción: ");
        Scanner choice = new Scanner(System.in);
        int numero = choice.nextInt();

        if (numero == 1) {
            System.out.println("Hola, opción 1");
        } else if (numero == 2) {
            System.out.println("Hola, opción 2");
        } else if (numero == 3) {
            System.out.println("Hola, opción 3");
        } else if (numero == 4) {
            System.out.println("Hola, opción 4");
        } else if (numero == 5) {
            System.out.println("Hola, opción 5");
        } else if (numero == 6) {
            System.out.println("Hola, opción 6");
        } else if (numero == 7) {
            System.out.println("Opción 7, finaliza el programa");

        } else {
            System.out.println("Opción inválida");
        }

    }
}
