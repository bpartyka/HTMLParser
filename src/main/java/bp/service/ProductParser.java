package bp.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductParser {

    final static String FILE_PATH = "src/main/resources/file.csv";

    public static File saveFile(String body) throws IOException {
        File file = saveRecordToFile(body);

        return file;
    }

    public static Integer getPriceOfProduct(String body) {
        Document doc = Jsoup.parseBodyFragment(body);
        Elements productPricesBox = doc.getElementsByClass("price-normal selenium-price-normal");

        String element = productPricesBox.first().toString();
        StringBuilder stringBuilder = new StringBuilder(element);

        int start = stringBuilder.indexOf("\n ");
        int end = stringBuilder.indexOf("&");
        element = element.substring(start + 1, end).replaceAll(" ", "");

        return Integer.parseInt(element);
    }

    public static String getNameOfProduct(String body) {
        Document doc = Jsoup.parseBodyFragment(body);
        Elements productPricesBox = doc.getElementsByClass("selenium-KP-product-name");

        String element = productPricesBox.first().toString();
        StringBuilder stringBuilder = new StringBuilder(element);

        int starrt = stringBuilder.indexOf("\">");
        int end = stringBuilder.indexOf("</h1");

        element = element.substring(starrt + 2, end);

        return element;
    }

    public static File saveRecordToFile(String body) {
        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[]
                {"PRIMARY_KEY", "NAME", "DESCRIPTION", "UPDATED_TIMESTAMP"});
        dataLines.add(new String[]
                {"1", getNameOfProduct(body), String.valueOf(getPriceOfProduct(body)), ""});

        File csvOutputFile = new File(FILE_PATH);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(data -> convertToCSV(data))
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return csvOutputFile;
    }

    public static String convertToCSV(String[] data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }
}
