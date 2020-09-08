package bp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GetHtmlService {

    public static String getHtmlBody(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity= restTemplate.getForEntity(url, String.class);

        return (responseEntity.getBody());
    }
}
