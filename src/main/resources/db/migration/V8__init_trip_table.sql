drop table if exists trip;
CREATE TABLE trip (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  tripStartDay VARCHAR(10),
  tripEndDay VARCHAR(10),
  tripStartHour VARCHAR(10),
  tripEndHour VARCHAR(10),
  tripStartLocation VARCHAR(25),
  tripEndLocation VARCHAR(25),
  tripStartCountry VARCHAR(25),
  tripEndCountry VARCHAR(25),
  tripStartVehicleOdometer INT NOT NULL,
  tripEndVehicleOdometer INT NOT NULL,
  card_id INT UNSIGNED,
  FOREIGN KEY (card_id) REFERENCES card (id)
)
