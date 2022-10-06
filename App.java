import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * App
 */
public class App {

    public static void main(String[] args) {

        try {
            URL url = new URL("https://ghibliapi.herokuapp.com/films/");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));

            System.out.println(httpConn.getResponseCode());
            System.out.println(httpConn.getResponseMessage());

            boolean done = false;
            String responseLine = "";
            String tempString = "";

            while (!done) {
                responseLine = in.readLine();
                if (responseLine == null) {
                    done = true;
                } else {
                    tempString = tempString + responseLine;
                }

            }
            JsonParser parser = new JsonParser();
            JsonArray movieArray = (JsonArray) parser.parse(tempString);

            for (JsonElement movies : movieArray) {
                System.out.println("Movie Title: " + movies.getAsJsonObject().get("title").getAsString());
            }

            Scanner scan = new Scanner(System.in);

            while (done = true) {
                System.out.print("\nSELECT A MOVIE FROM ABOVE:");
                String inputMovieTitle = scan.nextLine();

                if (tempString.contains(inputMovieTitle) && inputMovieTitle != "quit") {
                    for (JsonElement movieInfo : movieArray) {
                        if (movieInfo.getAsJsonObject().get("title").getAsString().equals(inputMovieTitle)) {
                            System.out.println(
                                    "\nMovie Title: " + movieInfo.getAsJsonObject().get("title").getAsString());
                            System.out.println(
                                    "Movie Description: "
                                            + movieInfo.getAsJsonObject().get("description").getAsString());
                            System.out.println(
                                    "Movie Release Date: "
                                            + movieInfo.getAsJsonObject().get("release_date").getAsString());
                            System.out.println(
                                    "Movie Producer: " + movieInfo.getAsJsonObject().get("producer").getAsString());
                        }
                    }

                } else if (inputMovieTitle.equals("quit")) {
                    System.out.println("User Quit...");
                    break;
                } else {
                    System.out.println("Error: Movie not found...");
                }
            }

        } catch (Exception e) {
            System.err.println("oops.");
            e.printStackTrace();
        }

    }

}
