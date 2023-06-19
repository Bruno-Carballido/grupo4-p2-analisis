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


import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static Pattern p = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    private static String[] pilotosLista = new String[20];
    private static Hash<String, User> usuarios = new HashImpl<>(30000);
    private static LinkedListImpl<User> usuariosLista = new LinkedListImpl<>();
    private static LinkedList<Tweet> tweets = new LinkedListImpl<>();
    private static Hash<String, Hashtag> hashtags = new HashImpl<>(1000);
//    private static LinkedList<Hashtag> hashtags = new LinkedListImpl<>();


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

            BufferedReader input = new BufferedReader(new FileReader("obligatorio2023/f1_dataset_test.csv"));
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
            String[] fecha;
            String fecha_str;
            String[] hashtagSplit;
            Hashtag[] hs;
            Hashtag hh;
            Double fav;

            while ((line = input.readLine()) != null) {// leo hasta salto de línea
                new_line = filaIncompleta.concat(line);
                fields = p.split(new_line, -1); // parseo campos por comillas
                if (fields.length == cantColumnas && cantComillasPar(new_line)) {
                    filaIncompleta = "";
                    // USUARIOS ------------
                    try {
                        u = usuarios.get(fields[1]);
                    } catch (Exception ex) {
                        try {
                            fav = Double.parseDouble(fields[7]);
                        } catch (Exception e) {
                            fav = 0d;
                        }
                        u = new User(idUsuario, fields[1], Boolean.parseBoolean(fields[8]), fav);
                        //u = new User(idUsuario, fields[1], Boolean.parseBoolean(fields[8]));
                        usuarios.put(fields[1], u);
                        usuariosLista.add(u);
                        idUsuario++;
                    }
                    // FIN USUARIOS ------------
                    // FECHAS ------------
                    try {
                        fecha_str = fields[9].split(" ", 0)[0];
                        fecha = fecha_str.split("-", -1);
                    } catch (Exception e) {
                        fecha = new String[]{"", "", ""};
                    }
                    // FIN FECHAS ------------
                    // HASHTAGS ------------
                    hashtagSplit = fields[11].replaceAll("(\\s+|'|\"|\\[|\\])", "").split(",", 1);
                    hs = new Hashtag[hashtagSplit.length];
                    for (int i = 0; i < hashtagSplit.length; i++) {
                        try {
                            hh = hashtags.get(hashtagSplit[i]);
                        } catch (Exception e) {
                            hh = new Hashtag(idHashtag, hashtagSplit[i]);
                            idHashtag++;
                        }
                        hs[i] = hh;
                    }
                    // HASHTAGS ------------
                    tweets.add(new Tweet(Long.parseLong(fields[0]), fields[10], fields[12], fecha, Boolean.parseBoolean(fields[13]), u, hs));
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
            System.out.printf("%s %s%s%n", "Tiempo de ejecución:", timeElapsed, "ms");
            System.out.println("------------------------");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
//        search10MostActivePilots("2021", "11");
//        search15UsersMostTweets();
        try {
            menu();
        } catch (DatosIncorrectos e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean cantComillasPar(String line) {
        return (line.length() - line.replace("\"", "").length()) % 2 == 0;
    }

    private static void search10MostActivePilots(String anio, String mes) {
        System.out.println("------------------------");
        try {
            long start = System.currentTimeMillis();

            Hash<String, Long> pilotosMentions = new HashImpl<>(25);
            for (String piloto : pilotosLista) {
                pilotosMentions.put(piloto, 0L);
            }

            for (int i = 0; i < tweets.size(); i++) {
                Tweet t = tweets.get(i);
                String[] f = t.getDate();
                String contenidoTweets = t.getContent();
                if (f[0].equals(anio) && f[1].equals(mes)) {
                    for (String piloto : pilotosLista) {
                        // long cant_menciones = (contenidoTweets.length() - (contenidoTweets.replaceAll(piloto, "").length())) / piloto.length();
                        long menciones_anteriores = pilotosMentions.get(piloto);
                        // pilotosMentions.update(piloto, menciones_anteriores + cant_menciones);
                        pilotosMentions.update(piloto, menciones_anteriores + (contenidoTweets.contains(piloto) ? 1 : 0));
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
public static String top7WithMoreFavourite(LinkedListImpl<User> usuariosLista, int count) throws DatosIncorrectos {
    if (usuariosLista.isEmpty()) {
        throw new DatosIncorrectos();}
    // Obtener los 7 usuarios con más favoritos
    User[] topUsers = new User[Math.min(usuariosLista.size(), count)];
    for (int i = 0; i < topUsers.length; i++) {
        User maxUser = null;
        double maxFavourites = Integer.MIN_VALUE;
        // Encontrar el usuario con más favoritos en cada iteración
        for (int j = 0; j < usuariosLista.size(); j++) {
            User user = usuariosLista.get(j);
            if (user.getFavourite() > maxFavourites && !containsUser(topUsers, user)) {
                maxUser = user;
                maxFavourites = user.getFavourite();}
        }
        topUsers[i] = maxUser;}
    // Obtener los nombres de los usuarios con más favoritos
    LinkedListImpl<User> usuariosFavoritos = new LinkedListImpl<>();
    for (User user : topUsers) {
        usuariosFavoritos.add(new User(user.getId(),user.getName(),user.isVerificado(), user.getFavourite()));}
    return usuariosFavoritos.toString();
}

private static boolean containsUser(User[] users, User user) {
        boolean flag = false;
        for (User u : users) {
            if (u != null && u.equals(user)) {
                flag = true;}
        }
        return flag;
    }
    public static int countTweetsWithWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la palabra a buscar: ");
        String word = scanner.nextLine().toLowerCase();
        System.out.println("------------------------");
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
        System.out.println(contador);
        return contador;
    }

    public static void menu() throws DatosIncorrectos {
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
            search10MostActivePilots("2021", "12");
        } else if (numero == 2) {
            System.out.println("Hola, opción 2");
        } else if (numero == 3) {
            System.out.println("Hola, opción 3");
        } else if (numero == 4) {
            System.out.println("Hola, opción 4");
        } else if (numero == 5) {
            System.out.println(top7WithMoreFavourite(usuariosLista, 7));
        } else if (numero == 6) {
            System.out.println(countTweetsWithWord());
        } else if (numero == 7) {
            System.out.println("Opción 7, finaliza el programa");

        } else {
            System.out.println("Opción inválida");
        }
    }
}
