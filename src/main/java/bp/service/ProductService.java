package bp.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ProductService {

    public String postFileToAnotherService(String url) throws IOException {
        String body = GetHtmlService.getHtmlBody(url);
        File file = ProductParser.saveFile(body);

        return PostCsvService.postConvertedFileToAnotherService(file);
    }
}
