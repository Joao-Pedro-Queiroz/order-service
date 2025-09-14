package store.order;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data @Builder @Accessors(fluent = true, chain = true)
public class Order {
  String id;
  String accountId;
  OffsetDateTime date;
  List<OrderItem> items;
  Double total;
}