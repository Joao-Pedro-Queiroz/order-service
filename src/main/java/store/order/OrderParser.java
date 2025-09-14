package store.order;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderParser {

  private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

  public static OrderOut toOut(Order o) {
    return o == null ? null :
      OrderOut.builder()
        .id(o.id())
        .date(ISO.format(o.date()))
        .items(o.items().stream().map(OrderParser::toItemOut).toList())
        .total(o.total())
        .build();
  }

  private static OrderItemOut toItemOut(OrderItem i) {
    return OrderItemOut.builder()
      .id(i.id())
      .product(ProductRefOut.builder().id(i.productId()).build())
      .quantity(i.quantity())
      .total(i.total())
      .build();
  }

  public static List<OrderSummaryOut> toSummary(List<Order> orders) {
    return orders.stream()
      .map(o -> OrderSummaryOut.builder()
        .id(o.id())
        .date(ISO.format(o.date()))
        .total(o.total())
        .build())
      .toList();
  }
}
