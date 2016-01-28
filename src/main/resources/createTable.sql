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

alter table Reservation ADD COLUMN subject TEXT;
alter table Payment ADD COLUMN PAYMENT_STATUS TEXT;
alter table Payment ADD COLUMN reservation_id INTEGER;
ALTER TABLE Payment ADD CONSTRAINT payment_reservations_fk FOREIGN KEY (reservation_id) REFERENCES Reservation (id);