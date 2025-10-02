package store.order;

import java.util.List;
import store.product.ProductOut;
import java.time.LocalDateTime;

public class OrderParser {

  public static OrderOut toOut(OrderModel om, List<ProductOut> products) {
    if (om == null) return null;

    var itemsOut = om.getItems().stream().map(im -> {
      ProductOut product = products.stream()
        .filter(p -> p.id().equals(im.getIdProduct()))
        .findFirst().orElse(null);

      return OrderItemOut.builder()
          .id(im.getId())
          .product(product)
          .quantity(im.getQuantity())
          .total(im.getTotal())
          .build();
    }).toList();

    return OrderOut.builder()
        .id(om.getId())
        .date(om.getDate().toString())
        .items(itemsOut)
        .total(om.getTotal())
        .build();
  }

  public static OrderModel toModel(OrderIn in, String idAccount) {
    OrderModel om = new OrderModel();
    om.setId(null);
    om.setDate(LocalDateTime.now());
    om.setIdAccount(idAccount);
    om.setTotal(0.0);
    return om;
  }
}
