import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
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
}
