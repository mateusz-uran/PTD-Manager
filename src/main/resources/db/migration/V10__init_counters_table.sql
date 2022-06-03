drop table if exists counters;
CREATE TABLE counters (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  counterTripEnd INT NOT NULL,
  counterTripStart INT NOT NULL,
  sumCounterMileage INT NOT NULL,
  sumCounterRefueling INT NOT NULL,
  card_id INT UNSIGNED,
  FOREIGN KEY (card_id) REFERENCES card (id)
)