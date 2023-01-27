drop table if exists counters;
CREATE TABLE counters (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  counterTripEnd INT NOT NULL DEFAULT 0,
  counterTripStart INT NOT NULL DEFAULT 0,
  sumCounterMileage INT NOT NULL DEFAULT 0,
  sumCounterRefueling INT NOT NULL DEFAULT 0,
  card_id INT UNSIGNED,
  FOREIGN KEY (card_id) REFERENCES card (id)
)