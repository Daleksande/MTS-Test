import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Set;

public class MTSParcing {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Let's go!");

        Movies movies = new Movies();
        Set<String> moviesId = movies.getMoviesId();

        System.out.println("List is done. Start to do .csv!");

        Date date = new Date();
        String filePath = "results/mts_" + date.getTime() + ".csv";
        File file = new File(filePath);

        BufferedWriter writer = Files.newBufferedWriter(Paths.get(String.valueOf(file)));
        CSVPrinter csvPrinter = new CSVPrinter(writer,
                CSVFormat.DEFAULT.withDelimiter(';').withHeader("ID", "NAME_RU", "NAME_EN", "YEAR", "COUNTRIES", "GENRES", "RATE", "FILM_DIRECTOR", "ACTORS"));

        for (String e : moviesId) {
            String id = e.replaceAll("[\"]", "");
            Movie movie = new Movie(id);

            String countries="", genres="", actors="";

            try {
                countries = movie.getCountries().toString().replaceAll("\\[", "").replaceAll("\\]", "");
            } catch (NullPointerException n) {
                System.out.println("NullPointerException was happened with countries array for id: " + id);
            }

            try {
                genres = movie.getGenres().toString().replaceAll("\\[", "").replaceAll("\\]", "");
            } catch (NullPointerException n) {
                System.out.println("NullPointerException was happened with genres array for id: " + id);
            }

            try {
                actors = movie.getGenres().toString().replaceAll("\\[", "").replaceAll("\\]", "");
            } catch (NullPointerException n) {
                System.out.println("NullPointerException was happened with actors array for id: " + id);
            }

            csvPrinter.printRecord(
                    movie.getId(),
                    movie.getNameRu(),
                    movie.getNameEng(),
                    movie.getYear(),
                    countries,
                    genres,
                    movie.getRate(),
                    movie.getDirector(),
                    actors
            );

            csvPrinter.flush();
        }

        System.out.println("File .csv is ready!");

    }
}