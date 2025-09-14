package store.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product", url = "http://product:8080")
public interface ProductClient {
  @GetMapping("/product/{id}")
  ResponseEntity<ProductOut> findById(@PathVariable("id") String id);
}