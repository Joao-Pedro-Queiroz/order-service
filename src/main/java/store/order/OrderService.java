package store.order;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import store.product.ProductOut;

@Service
public class OrderService {

  @Autowired private OrderRepository orderRepository;
  @Autowired private ProductClient productClient;
  @Autowired private CurrentUser currentUser;

  public Order create(OrderIn in) {
    if (in == null || in.items() == null || in.items().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Items are mandatory");
    }

    OrderModel om = new OrderModel()
        .accountId(currentUser.id())
        .date(OffsetDateTime.now())
        .total(0.0);

    List<OrderItemModel> items = new ArrayList<>();
    double total = 0.0;

    for (OrderItemIn it : in.items()) {
      if (it.quantity() == null || it.quantity() <= 0) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be positive");
      }

      ProductOut p;
      try {
        p = productClient.findById(it.idProduct()).getBody();
      } catch (Exception e) {
        // requisito: 400 se o produto não existir
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product does not exist");
      }
      if (p == null || p.price() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product does not exist");
      }

      double line = p.price() * it.quantity();
      total += line;

      items.add(new OrderItemModel()
          .order(om)
          .productId(p.id())
          .quantity(it.quantity())
          .total(line));
    }

    om.items(items).total(total);
    return orderRepository.save(om).to();
  }

  public List<Order> findAll() {
    return orderRepository.findAllByAccountIdOrderByDateDesc(currentUser.id())
        .stream().map(OrderModel::to).toList();
  }

  public Order findById(String id) {
    OrderModel om = orderRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

    if (!om.accountId().equals(currentUser.id())) {
      // requisito: 404 se o pedido não for do usuário atual
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
    }
    return om.to();
  }
}
