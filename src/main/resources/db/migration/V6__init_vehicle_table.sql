DROP TABLE IF EXISTS vehicle;
CREATE TABLE vehicle (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    truckMainModel VARCHAR(30),
    truckLicensePlate VARCHAR(30),
    truckType VARCHAR(30),
    trailerLicensePlate VARCHAR(30),
    trailerType VARCHAR(30),
    leftTankFuelCapacity INT,
    rightTankFuelCapacity INT,
    adBlueCapacity INT,
    trailerCapacity INT,
    vehicleImageName VARCHAR(30),
    vehicleImageDescription VARCHAR(30),
    vehicleImagePath VARCHAR(64),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users (id)
)