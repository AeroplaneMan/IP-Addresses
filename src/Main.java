import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // get user input (integers)
        Scanner input = new Scanner(System.in);
        boolean isMark = true;
        String ipAddress = "";
        try {
            System.out.println("Please enter the first octet:");
            int oct1 = input.nextInt();

            System.out.println("Please enter the second octet:");
            int oct2 = input.nextInt();

            System.out.println("Please enter the third octet:");
            int oct3 = input.nextInt();

            System.out.println("Please enter the fourth octet:");
            int oct4 = input.nextInt();
            int[] octs = {oct1, oct2, oct3, oct4};

            for (int i = 0; i < 4; i++){
                if (octs[i] < 0 || octs[i] > 255){
                    System.out.println("Octet " + (i + 1) + " is invalid.");
                    isMark = false;
                }
            }

            if (isMark){
                ipAddress = oct1+"."+oct2+"."+oct3+"."+oct4+".";
                // display output
                System.out.println("IP Address: " + ipAddress);
                String geoLocationData = getGeoLocation(ipAddress);
                System.out.println(geoLocationData);
            }
        }
        catch(Exception e){
                System.out.println("Try again dummy \uD83E\uDD21");
        }

    } // main() method closing

    // the api stuff we'll do later
    public static String getGeoLocation(String ipAddress){
        String apiKey = "a33e74b51990ebbc240c6ff949ff62fb"; // ms. paulino's key
        // constructing the url for the API request
        String url = "http://api.ipstack.com/" + ipAddress + "?access_key=" + apiKey;

        // create an HTTP client so we can send a request
        HttpClient client = HttpClient.newHttpClient();

        // build an HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            // send the request to the API, and get a response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

}