package ru.samsung.itschool.mdev;

import com.google.gson.Gson;
import ru.samsung.itschool.mdev.model.Weather;
import ru.samsung.itschool.mdev.model.Weather__1;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        // API - Application Program Interface
        // 7085ab35dfcb32bf6b62c323a6f8d589
        // HTTPS - протокол передачи гипертекста (SSL)
        // GET запросы и POST запросы
        // JSON - Java Script Object Notation

        //
        // https://api.openweathermap.org/data/2.5/weather?
        // q=Moscow
        // &
        // appid=7085ab35dfcb32bf6b62c323a6f8d589

        String town = "Moscow";
        String key = "7085ab35dfcb32bf6b62c323a6f8d589";
        String https = "https://api.openweathermap.org/data/2.5/weather?q="+town+"&appid="+key;

        HttpsURLConnection connection;
        URL u = null;
        try {
            u = new URL(https);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        connection = (HttpsURLConnection) u.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.connect();

        int status = connection.getResponseCode();
        ArrayList<String> lines = new ArrayList<>();
        if(status == 200) {
            Scanner scan = new Scanner(connection.getInputStream());
            while(scan.hasNext()) {
                String line = scan.nextLine();
                lines.add(line);
            }
        }

        for(String s: lines) {
        //    System.out.println(s);
        }
        String pathtosave = "c:\\Users\\Pavel\\IdeaProjects\\untitled168\\result.txt";
        Path path = Path.of(pathtosave);
        Files.createFile(path);
        Files.write(path,lines);

        List<String> readlines = Files.readAllLines(path);
        Gson gson = new Gson();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<readlines.size();i++) {
            stringBuilder.append(readlines.get(i));
        }
        Weather weather =  gson.fromJson(stringBuilder.toString(),Weather.class);
        List<Weather__1> list = weather.getWeather();
        System.out.println(list.get(0).getDescription());
        System.out.println(list.get(0).getMain());

     // https://www.jsonschema2pojo.org/

    }
}
