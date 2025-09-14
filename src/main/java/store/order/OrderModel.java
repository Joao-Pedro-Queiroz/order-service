package store.order;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "order")
@Setter @Accessors(chain = true, fluent = true)
@NoArgsConstructor @AllArgsConstructor
public class OrderModel {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "account_id")
  private String accountId;

  @Column(name = "date_utc")
  private OffsetDateTime date;

  @Column(name = "total")
  private Double total;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<OrderItemModel> items = new ArrayList<>();

  public OrderModel(Order o) {
    this.id = o.id();
    this.accountId = o.accountId();
    this.date = o.date();
    this.total = o.total();
    this.items = o.items().stream().map(i -> new OrderItemModel(i).order(this)).toList();
  }

  public Order to() {
    return Order.builder()
      .id(this.id)
      .accountId(this.accountId)
      .date(this.date)
      .total(this.total)
      .items(this.items.stream().map(OrderItemModel::to).toList())
      .build();
  }
}