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
  PAYMENT_DATE DATE
);

insert into payment (from_email, to_email, amount, payment_date) values ('a','b', 2.00, current_date);
