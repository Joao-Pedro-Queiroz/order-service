package store.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class OrderResource implements OrderController {

  @Autowired private OrderService orderService;

  @Override
  public ResponseEntity<OrderOut> create(OrderIn in) {
    var created = orderService.create(in);
    var out = OrderParser.toOut(created);
    var location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(out.id())
                    .toUri();
    return ResponseEntity.created(location).body(out); // 201
  }

  @Override
  public ResponseEntity<List<OrderSummaryOut>> findAll() {
    return ResponseEntity.ok(OrderParser.toSummary(orderService.findAll()));
  }

  @Override
  public ResponseEntity<OrderOut> findById(String id) {
    return ResponseEntity.ok(OrderParser.toOut(orderService.findById(id)));
  }
}
