package bp.controller;

import bp.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/v1/product")
    public String postFileToAnotherService(@RequestParam("url") String url) throws IOException {

        return productService.postFileToAnotherService(url);
    }
}
