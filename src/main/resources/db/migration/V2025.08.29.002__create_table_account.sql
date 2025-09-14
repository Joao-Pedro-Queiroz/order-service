create table order (
  id varchar(36) primary key,
  account_id varchar(36) not null,
  date_utc timestamptz not null default now(),
  total numeric(12,2) not null
);

create table order_item (
  id varchar(36) primary key,
  order_id varchar(36) not null,
  product_id varchar(36) not null,
  quantity integer not null check (quantity > 0),
  total numeric(12,2) not null,
  constraint fk_order_item__order foreign key (order_id)
    references order(id) on delete cascade
);