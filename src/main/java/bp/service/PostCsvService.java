package bp.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PostCsvService {

    final static String URL = "http://127.0.0.1:10090/v1/import/csv";

    public static String postConvertedFileToAnotherService(File file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, File> body
                = new LinkedMultiValueMap<>();
        body.add("file", file);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        byte[] bArray = Files.readAllBytes(Paths.get(file.getPath()));

        LinkedMultiValueMap<String, String> pdfHeaderMap = new LinkedMultiValueMap<>();
        pdfHeaderMap.add("Content-disposition", "form-data; name=file; filename=" + file.getName());
        pdfHeaderMap.add("Content-type", "application/css");
        HttpEntity<byte[]> doc = new HttpEntity<byte[]>(bArray, pdfHeaderMap);

        LinkedMultiValueMap<String, Object> multipartReqMap = new LinkedMultiValueMap<>();
        multipartReqMap.add("file", doc);

        HttpEntity<LinkedMultiValueMap<String, Object>> reqEntity = new HttpEntity<>(multipartReqMap, headers);
        restTemplate.exchange(URL, HttpMethod.POST, reqEntity, String.class);

        return reqEntity.toString();
    }
}
