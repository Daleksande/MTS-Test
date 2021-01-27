import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.util.*;

public class Movies {

    private Set<String> moviesId;

    Movies() throws IOException, InterruptedException {
        this.moviesId = getMovies();
    }

    public Set<String> getMoviesId() {
        return moviesId;
    }

    public Set<String> getMovies() throws IOException, InterruptedException {

        int offset = 0;
        int total = 1;

        Set<String> idSet = new HashSet<String>();

        while (total > offset) {

            Request request = new Request();

            String url = "https://api.mtstv.ru/v2/segments/9d69f966-068d-4e09-9fb2-7c84d467362c/items?limit=20&offset=" + offset;

            String jsonBody = request.getRequestJsonBody(url);

            JsonObject jsonObject = new JsonParser().parse(jsonBody).getAsJsonObject();

            JsonArray data = (JsonArray) jsonObject.get("data");
            for (int i = 0; i < data.size(); i++) {
                String e = data.get(i).getAsJsonObject().get("id").toString();
                idSet.add(e);
            }

            String metaTotal = jsonObject.get("meta").getAsJsonObject().get("pagination").getAsJsonObject().get("total").toString();

            total = Integer.parseInt(metaTotal);

            offset = offset + 20;
        }

        return idSet;
    }

}
