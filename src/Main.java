import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {


        try {
            BufferedReader input = new
                    BufferedReader(new FileReader("obligatorio2023/f1_dataset_test.csv"));
            String line = null;

            String[] rows = input.readLine().split(",");
            int cantColumnas = rows.length;
            System.out.println(cantColumnas);

            int cantColumnasActual = 0;

            ArrayList<String[]> tmp = new ArrayList<>();
            StringBuilder filaIncompleta = new StringBuilder();

            while ((line = input.readLine()) != null) {// leo hasta salto de línea
                String new_line = filaIncompleta + line;
                String[] fields = parseCSVLine(new_line); // parseo campos por comillas
                if (fields.length == cantColumnas && cantComillasPar(new_line)) {
                    filaIncompleta = new StringBuilder();
                    tmp.add(fields);
                    //System.out.println(Arrays.toString(fields));
                } else if (fields.length > cantColumnas && cantComillasPar(new_line)) {
                    filaIncompleta.setLength(0);
                } else {
                    filaIncompleta.append(line);
                }
            }
            input.close();
            System.out.println(tmp.size());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        menu();
    }

    public static String[] parseLineRecursive(BufferedReader input, String line, int cantColumnas, int cantColumnasActual) {
        String[] fields = parseCSVLine(line); // parseo campos por comillas
        if (fields.length + cantColumnasActual == cantColumnas && cantComillasPar(line)) {
            return fields;
        } else {
            try {
                return parseLineRecursive(input, line + input.readLine(), cantColumnas, fields.length);
            } catch (Exception e) {
                return parseLineRecursive(input, line, cantColumnas, fields.length);
            }
        }
    }

    public static String[] parseCSVLine(String line) {
        // Create a pattern to match breaks
        Pattern p = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        // Split input with the pattern
        String[] fields = p.split(line, -1);
        for (int i = 0; i < fields.length; i++) {
            // Get rid of residual double quotes
            fields[i] = fields[i].replace("\"", "");
        }
        return fields;
    }

    public static boolean cantComillasPar(String line) {
        int count = line.length() - line.replace("\"", "").length();
        return count % 2 == 0;
    }
    public static void menu (){
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
