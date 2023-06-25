import entities.Hashtag;
import entities.Tweet;
import entities.User;
import uy.edu.um.prog2.adt.Hash;
import uy.edu.um.prog2.adt.HashImpl;
import uy.edu.um.prog2.adt.LinkedList;
import uy.edu.um.prog2.adt.LinkedListImpl;
import uy.edu.um.prog2.adt.exceptions.DatosIncorrectos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static Pattern p = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    private static String[] pilotosLista = new String[20];
    private static Hash<String, User> usuarios = new HashImpl<>(80000);
    private static LinkedListImpl<User> usuariosLista = new LinkedListImpl<>();
    private static LinkedList<Tweet> tweets = new LinkedListImpl<>();
    private static Hash<String, Hashtag> hashtags = new HashImpl<>(10000);
    private static LinkedList<String> hashtagsLista = new LinkedListImpl<>();


    public static void main(String[] args) {
        System.out.println("INICIO MAIN");
        long start = System.currentTimeMillis();

        try {
            BufferedReader drivers = new BufferedReader(new FileReader("obligatorio2023/drivers.txt"));
            String line;

            // Carga de pilotos ------------
            int cant_pilotos = 0;
            while ((line = drivers.readLine()) != null) {
                pilotosLista[cant_pilotos] = line;
                cant_pilotos++;
            }
            // Fin carga de pilotos ------------

            BufferedReader input = new BufferedReader(new FileReader("obligatorio2023/f1_dataset.csv"));
            String[] rows = input.readLine().split(",");
            line = null;
            int cantColumnas = rows.length;

            int idUsuario = 0;
            User u;

            int idHashtag = 0;

            //StringBuilder filaIncompleta = new StringBuilder();
            String filaIncompleta = "";
            String new_line;
            String[] fields;
            LocalDate fecha;
            String fechaStr;
            String[] fStrSpli;
            String[] hashtagSplit;
            String hashtagSplitILowerCase;
            Hashtag hh;
            Double fav;
            Tweet tw;

            while ((line = input.readLine()) != null) {// leo hasta salto de línea
                new_line = filaIncompleta.concat(line);
                fields = p.split(new_line, -1); // parseo campos por comas
                if (fields.length == cantColumnas && fields[0].length() <= 6) {
                    filaIncompleta = "";
                    // FECHAS ------------
                    try {
                        fechaStr = fields[9].split(" ", 0)[0];
                        fStrSpli = fechaStr.split("-", -1);
                        fecha = LocalDate.of(Integer.parseInt(fStrSpli[0]), Integer.parseInt(fStrSpli[1]), Integer.parseInt(fStrSpli[2]));
                    } catch (Exception e) {
                        fecha = null;
                    }
                    // FIN FECHAS ------------
                    // USUARIOS ------------
                    try {
                        fav = Double.parseDouble(fields[7]);
                    } catch (Exception e) {
                        fav = 0d;
                    }
                    try {
                        u = usuarios.get(fields[1]);
                        if (u.getUpdateDate().isBefore(fecha)) {
                            u.setUpdateDate(fecha);
                            u.setFavourite(fav);
                        }
                    } catch (Exception ex) {
                        u = new User(idUsuario, fields[1], Boolean.parseBoolean(fields[8]), fav, fecha);
                        usuarios.put(fields[1], u);
                        usuariosLista.add(u);
                        idUsuario++;
                    }
                    // FIN USUARIOS ------------
                    tw = new Tweet(Long.parseLong(fields[0]), fields[10], fields[12], fecha, Boolean.parseBoolean(fields[13]), u);
                    tweets.add(tw);
                    // HASHTAGS ------------
                    hashtagSplit = fields[11].replaceAll("(\\s+|'|\"|\\[|\\])", "").split(",", -1);
                    for (int i = 0; i < hashtagSplit.length; i++) {
                        hashtagSplitILowerCase = hashtagSplit[i].toLowerCase();
                        try {
                            hh = hashtags.get(hashtagSplitILowerCase);
                        } catch (Exception e) {
                            hh = new Hashtag(idHashtag, hashtagSplit[i]);
                            hashtagsLista.add(hashtagSplitILowerCase);
                            hashtags.put(hashtagSplitILowerCase, hh);
                            idHashtag++;
                        }
                        hh.addTweets(tw);
                    }
                    // HASHTAGS ------------
                } else {
                    filaIncompleta = filaIncompleta.concat(line);
                }
            }
            input.close();

            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;

            System.out.println("------------------------");
            System.out.printf("%s %s%n", "Cantidad de Tweets:", tweets.size());
            System.out.printf("%s %s%n", "Cantidad de usuarios:", idUsuario);
            System.out.printf("%s %s%s%n", "Tiempo de carga:", timeElapsed, "ms");
            System.out.println("------------------------");

            menu();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static boolean cantComillasPar(String line) {
        return (line.length() - line.replace("\"", "").length()) % 2 == 0;
    }

    private static void search10MostActivePilots(Integer anio, Integer mes) {
        clearScreen();
        System.out.println("------------------------");
        try {
            long start = System.currentTimeMillis();

            Hash<String, Long> pilotosMentions = new HashImpl<>(25);
            for (String piloto : pilotosLista) {
                pilotosMentions.put(piloto, 0L);
            }

            long mencionesAnteriores;
            String[] pilotoSpl;

            for (int i = 0; i < tweets.size(); i++) {
                Tweet t = tweets.get(i);
                LocalDate f = t.getDate();
                String contenidoTweets = t.getContent();
                if (f != null && YearMonth.from(f).equals(YearMonth.of(anio, mes))) {
                    for (String piloto : pilotosLista) {
                        // long cant_menciones = (contenidoTweets.length() - (contenidoTweets.replaceAll(piloto, "").length())) / piloto.length();
                        mencionesAnteriores = pilotosMentions.get(piloto);
                        pilotoSpl = piloto.split(" ", -1);
                        // pilotosMentions.update(piloto, menciones_anteriores + cant_menciones);
                        pilotosMentions.update(piloto, mencionesAnteriores + ((contenidoTweets.contains(pilotoSpl[0]) || (contenidoTweets.contains(pilotoSpl[pilotoSpl.length - 1])) ? 1 : 0)));
                    }
                }
            }

            String[] pListAux = pilotosLista;
            int n = pilotosLista.length;

            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (pilotosMentions.get(pListAux[j]) > pilotosMentions.get(pListAux[j + 1])) {
                        String temp = pListAux[j];
                        pListAux[j] = pListAux[j + 1];
                        pListAux[j + 1] = temp;
                    }
                }
                if (i < 10) System.out.printf("%s %s%n", pListAux[n - i - 1], pilotosMentions.get(pListAux[n - i - 1]));
                else break;
            }

            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println("------------------------");
            System.out.printf("%s %s%s%n", "Tiempo de ejecución:", timeElapsed, "ms");
            System.out.println("------------------------");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void search15UsersMostTweets() {
        clearScreen();
        System.out.println("------------------------");
        try {
            long start = System.currentTimeMillis();

            String[] usuariosLst = new String[usuarios.size()];
            Hash<String, Long> userTweets = new HashImpl<>(13000);

            int indexUsu = 0;
            for (int i = 0; i < tweets.size(); i++) {
                Tweet t = tweets.get(i);
                String nUsu = t.getUsuario().getName();
                Long cant = 0L;
                try {
                    cant = userTweets.get(nUsu);
                    userTweets.update(nUsu, cant + 1);
                } catch (Exception ex) {
                    userTweets.put(nUsu, 1L);
                    usuariosLst[indexUsu] = nUsu;
                    indexUsu++;
                }
            }
            int n = usuariosLst.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (userTweets.get(usuariosLst[j]) > userTweets.get(usuariosLst[j + 1])) {
                        String temp = usuariosLst[j];
                        usuariosLst[j] = usuariosLst[j + 1];
                        usuariosLst[j + 1] = temp;
                    }
                }
                if (i < 15) {
                    System.out.println(usuariosLst[n - i - 1]);
                    System.out.printf("\t%s Tweets\n\tVerificado: %s%n",
                            userTweets.get(usuariosLst[n - i - 1]),
                            usuarios.get(usuariosLst[n - i - 1]).isVerificado() ? "Si" : "No");
                } else break;
            }
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println("------------------------");
            System.out.printf("%s %s%s%n", "Tiempo de ejecución:", timeElapsed, "ms");
            System.out.println("------------------------");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void distictCantHashtagsDay(Integer year, Integer month, Integer day) throws DatosIncorrectos {
        clearScreen();
        System.out.println("------------------------");
        long start = System.currentTimeMillis();

        LinkedList<Hashtag> hashtagsDay = new LinkedListImpl<>();

        Hashtag h;
        LinkedList<Tweet> tweetsHashtag;
        LocalDate fechaComparar = LocalDate.of(year, month, day);
        LocalDate f;
        for (int i = 0; i < hashtagsLista.size(); i++) {
            try {
                h = hashtags.get(hashtagsLista.get(i));
                tweetsHashtag = h.getTweets();
                for (int j = 0; j < tweetsHashtag.size(); j++) {
                    f = tweetsHashtag.get(j).getDate();
                    if (f != null && f.equals(fechaComparar)) {
                        hashtagsDay.add(h);
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.printf("%s %d-%d-%d: %s%n", "Cantidad de hashtags diferentes para el día", year, month, day, hashtagsDay.size());
        System.out.println("------------------------");
        System.out.printf("%s %s%s%n", "Tiempo de ejecución:", timeElapsed, "ms");
        System.out.println("------------------------");
    }

    public static void mostUsedHashtag(Integer year, Integer month, Integer day) {
        long start = System.currentTimeMillis();

//        Heap<Long, String> hashtagsFechas = new HeapImpl<>();

        Hashtag h;
        LinkedList<Tweet> tweetsHashtag;
        Long cantTweets = 0L;
        LocalDate fechaComparar = LocalDate.of(year, month, day);
        LocalDate f;
        String maxHashtagDay = "";
        Long maxCantTweetsHS = 0L;
        for (int i = 0; i < hashtagsLista.size(); i++) {
            try {
                h = hashtags.get(hashtagsLista.get(i));
                if (!h.getText().equalsIgnoreCase("f1")) {
                    tweetsHashtag = h.getTweets();
                    for (int j = 0; j < tweetsHashtag.size(); j++) {
                        f = tweetsHashtag.get(j).getDate();
                        if (f != null && f.equals(fechaComparar))
                            cantTweets++;
                    }
                    if (cantTweets > maxCantTweetsHS) {
                        maxCantTweetsHS = cantTweets;
                        maxHashtagDay = h.getText();
                    }
//                    hashtagsFechas.insert(cantTweets, h.getText());
                    cantTweets = 0L;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.printf("Hashtag más usado para el día %d-%d-%d: %s (%d veces)%n", year, month, day, maxHashtagDay, maxCantTweetsHS);
        System.out.println("------------------------");
        System.out.printf("%s %s%s%n", "Tiempo de ejecución:", timeElapsed, "ms");
        System.out.println("------------------------");
    }


    public static void top7WithMoreFavourite() throws DatosIncorrectos {
        clearScreen();
        System.out.println("------------------------");
        long start = System.currentTimeMillis();
        if (usuariosLista.isEmpty()) {
            throw new DatosIncorrectos();
        }
        User[] topUsers = new User[7];
        User maxUser = null;
        for (int i = 0; i < topUsers.length; i++) {
            double maxFavourites = Integer.MIN_VALUE;
            // Encontrar el usuario con más favoritos en cada iteración
            for (int j = 0; j < usuariosLista.size(); j++) {
                User user = usuariosLista.get(j);
                if (user.getFavourite() > maxFavourites && !containsUser(topUsers, user)) {
                    maxUser = user;
                    maxFavourites = user.getFavourite();
                }
            }
            topUsers[i] = maxUser;
        }
        // Obtener los nombres de los usuarios con más favoritos
        LinkedListImpl<User> usuariosFavoritos = new LinkedListImpl<>();
        for (User user : topUsers) {
            usuariosFavoritos.add(user);
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(usuariosFavoritos.toString());
        System.out.println("------------------------");
        System.out.printf("%s %s%s%n", "Tiempo de ejecución:", timeElapsed, "ms");
        System.out.println("------------------------");
    }

    private static boolean containsUser(User[] users, User user) {
        boolean flag = false;
        for (User u : users) {
            if (u != null && u.equals(user)) {
                flag = true;
            }
        }
        return flag;
    }

    public static void countTweetsWithWord(String word) {
        clearScreen();
        System.out.println("------------------------");
        long start = System.currentTimeMillis();

        int contador = 0;
        int n = tweets.size();
        Tweet tweet;
        for (int i = 0; i < n; i++) {
            try {
                tweet = tweets.get(i);
                if (tweet.getContent().toLowerCase().contains(word)) {
                    contador++;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.printf("Cantidad de veces que se menciona la palabra \"%s\": %d%n", word, contador);
        System.out.println("------------------------");
        System.out.printf("%s %s%s%n", "Tiempo de ejecución:", timeElapsed, "ms");
        System.out.println("------------------------");
    }

    public static void menu() throws DatosIncorrectos {
        int numero = 0;
        Scanner choice;
        while (numero != 7) {
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
            choice = new Scanner(System.in);
            numero = choice.nextInt();

            if (numero == 1) {
                clearScreen();
                System.out.print("Ingrese año a buscar: ");
                choice = new Scanner(System.in);
                Integer anio = choice.nextInt();
                System.out.print("Ingrese mes a buscar: ");
                choice = new Scanner(System.in);
                Integer mes = choice.nextInt();
                search10MostActivePilots(anio, mes);
            } else if (numero == 2) {
                search15UsersMostTweets();
            } else if (numero == 3) {
                clearScreen();
                System.out.print("Ingrese fecha a buscar en formato YYYY-MM-DD: ");
                choice = new Scanner(System.in);
                String fecha = choice.nextLine();
                try {
                    String[] fechaSplit = fecha.split("-", -1);
                    distictCantHashtagsDay(Integer.parseInt(fechaSplit[0]), Integer.parseInt(fechaSplit[1]), Integer.parseInt(fechaSplit[2]));
                } catch (Exception e) {
                    System.out.println("Datos incorrectos");
                }
            } else if (numero == 4) {
                clearScreen();
                System.out.print("Ingrese fecha a buscar en formato YYYY-MM-DD: ");
                choice = new Scanner(System.in);
                String fecha = choice.nextLine();
                try {
                    String[] fechaSplit = fecha.split("-", -1);
                    mostUsedHashtag(Integer.parseInt(fechaSplit[0]), Integer.parseInt(fechaSplit[1]), Integer.parseInt(fechaSplit[2]));
                } catch (Exception e) {
                    System.out.println("Datos incorrectos");
                }
            } else if (numero == 5) {
                top7WithMoreFavourite();
            } else if (numero == 6) {
                clearScreen();
                System.out.print("Ingrese la palabra a buscar: ");
                choice = new Scanner(System.in);
                String word = choice.nextLine().toLowerCase();
                countTweetsWithWord(word);
            } else if (numero == 7) {
                clearScreen();
                System.out.println("Opción 7, finaliza el programa");
            } else {
                clearScreen();
                System.out.println("Opción inválida");
            }
        }
    }
}
