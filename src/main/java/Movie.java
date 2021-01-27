import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;

public class Movie {
    private String id;
    private String nameRu;
    private String nameEng;
    private String year;
//    private String studio;
    private ArrayList<String> countries;
    private ArrayList<String> genres;
    private String rate;
//    private String receiptsWorld;
//    private String receiptsRu;
    private String director;
    private String[] actors;
//    private String[] rewords;
//    private String releaseDateRu;
//    private String releaseDateDigital;

    public Movie (String id) throws IOException, InterruptedException {
        this.id = id;
        JsonObject jsonObject = getMovieJson(id);
        this.nameRu = setNameRu(jsonObject);
        this.nameEng = setNameEng(jsonObject);
        this.year = setYear(jsonObject);
        this.countries = setCountries(jsonObject);
        this.genres = setGenres(jsonObject);
        this.rate = setRate(jsonObject);
        this.director = setDirector(jsonObject);
        this.actors = setActors(jsonObject);
    }

    public String getId() {
        return id;
    }

    private String setNameRu (JsonObject jsonObject) {

        try {
            String nameRu = jsonObject.get("data").getAsJsonObject().get("attributes").getAsJsonObject().get("name").toString().replaceAll("[\"]", "");
            return nameRu;
        } catch (Exception e) {
            return null;
        }
    }

    public String getNameRu() {
        return nameRu;
    }

    private String setNameEng (JsonObject jsonObject) {

        try {
            String nameEng = jsonObject.get("data").getAsJsonObject().get("attributes").getAsJsonObject().get("original_name").toString().replaceAll("[\"]", "");
            return nameEng;
        } catch (Exception e) {
            return null;
        }
    }

    public String getNameEng() {
        return nameEng;
    }

    private String setYear (JsonObject jsonObject) {

        try {
            String year = jsonObject.get("data").getAsJsonObject().get("attributes").getAsJsonObject().get("year").toString().replaceAll("[\"]", "");
            return year;
        } catch (Exception e) {
            return null;
        }
    }

    public String getYear() {
        return year;
    }

    private ArrayList<String> setCountries (JsonObject jsonObject) {

        try {
            JsonArray countries = jsonObject.get("data").getAsJsonObject().get("attributes").getAsJsonObject().get("countries").getAsJsonArray();
            ArrayList<String> arrCountries = new ArrayList<String>();
            for (int i = 0; i < countries.size(); i++) {
                String e = countries.get(i).toString().replaceAll("[\"]", "");
                arrCountries.add(e);
            }
            return arrCountries;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    private ArrayList<String> setGenres (JsonObject jsonObject) {

        try {
            JsonArray genres = jsonObject.get("data").getAsJsonObject().get("relationships").getAsJsonObject().get("genres").getAsJsonObject().get("data").getAsJsonArray();
            ArrayList<String> arrGenres = new ArrayList<String>();
            for (int i = 0; i < genres.size(); i++) {
                String e = genres.get(i).getAsJsonObject().get("id").toString().replaceAll("[\"]", "");
                arrGenres.add(e);
            }
            return arrGenres;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    private String setRate (JsonObject jsonObject) {

        try {
            JsonArray rate = jsonObject.get("data").getAsJsonObject().get("attributes").getAsJsonObject().get("ratings").getAsJsonArray();
            String e = rate.get(0).getAsJsonObject().get("rating").toString().replaceAll("[\"]", "");
            return e;
        } catch (Exception e) {
            return null;
        }
    }

    public String getRate() {
        return rate;
    }

    private String setDirector (JsonObject jsonObject) {

        try {
            JsonArray director = jsonObject.get("data").getAsJsonObject().get("attributes").getAsJsonObject().get("directors").getAsJsonArray();
            String e = director.get(0).getAsJsonObject().get("name").toString().replaceAll("[\"]", "");
            return e;
        } catch (Exception e) {
            return null;
        }
    }

    public String getDirector() {
        return director;
    }

    private String[] setActors (JsonObject jsonObject) {

        try {
            JsonArray actors = jsonObject.get("data").getAsJsonObject().get("attributes").getAsJsonObject().get("actors").getAsJsonArray();
            String[]  arrActors = new String[5];
            for (int i = 0; i < actors.size() & i < 5; i++) {
                String e = actors.get(i).getAsJsonObject().get("name").toString().replaceAll("[\"]", "");
                arrActors[i] = e;
            }
            return arrActors;
        } catch (Exception e) {
            return null;
        }
    }

    public String[] getActors() {
        return actors;
    }

    private static JsonObject getMovieJson(String id) throws InterruptedException {

        try {

            Request request = new Request();
            String url = "https://api.mtstv.ru/v2/content/" + id;
            String jsonBody = request.getRequestJsonBody(url);

            JsonObject jsonObject = new JsonParser().parse(jsonBody).getAsJsonObject();
            return jsonObject;
        } catch (IOException e) {
            System.out.println("IOException was happened during getting JSON");
            return null;
        }
    }

}
