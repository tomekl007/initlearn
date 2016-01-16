heroku pg:psql

CREATE TABLE Record(
   ID INT PRIMARY KEY     NOT NULL,
   DATA           TEXT
);

CREATE TABLE Payment(
  ID SERIAL PRIMARY KEY ,
  FROM_EMAIL TEXT,
  TO_EMAIL TEXT,
  AMOUNT DECIMAL(10,2),
  PAYMENT_DATE timestamp
);

insert into payment (from_email, to_email, amount, payment_date) values ('a','b', 2.00, current_date);

CREATE TABLE Reservation(
  ID SERIAL PRIMARY KEY ,
  teacher TEXT,
  reserved_by TEXT,
  from_hour timestamp,
  to_hour TIMESTAMP
);
