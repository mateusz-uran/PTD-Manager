drop table if exists fuel;
CREATE TABLE fuel (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  refueling_date VARCHAR(10),
  refueling_location VARCHAR(20),
  vehicle_odometer INT,
  fuel_amount INT NOT NULL,
  card_id INT UNSIGNED,
  FOREIGN KEY (card_id) REFERENCES card (id)
)