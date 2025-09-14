package store.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "order_item")
@Setter @Accessors(chain = true, fluent = true)
@NoArgsConstructor @AllArgsConstructor
public class OrderItemModel {
    
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "order_id")
  private OrderModel order;

  @Column(name = "product_id")
  private String productId;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "total")
  private Double total;

  public OrderItemModel(OrderItem oi) {
    this.id = oi.id();
    this.productId = oi.productId();
    this.quantity = oi.quantity();
    this.total = oi.total();
  }

  public OrderItem to() {
    return OrderItem.builder()
      .id(this.id)
      .productId(this.productId)
      .quantity(this.quantity)
      .total(this.total)
      .build();
  }
}
