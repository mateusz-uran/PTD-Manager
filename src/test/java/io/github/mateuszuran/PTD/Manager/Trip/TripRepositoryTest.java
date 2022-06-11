package io.github.mateuszuran.PTD.Manager.Trip;

import io.github.mateuszuran.PTD.Manager.Card.Card;
import io.github.mateuszuran.PTD.Manager.Card.CardRepository;
import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
class TripRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(TripRepositoryTest.class);
    @Autowired
    CardRepository cardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TripRepository tripRepository;

    @Test
    @Transactional
    void shouldGetAllTripsByCardIdAndOrderedByStartCounter() {
        userRepository.save(new User(anyInt(), "bar@o2.pl", "foobar", "John", "Smith"));
        User user = userRepository.findByEmail("bar@o2.pl");
        cardRepository.save(new Card(anyInt(), "trips", "driver", "12.11.1997", true, user));
        Card cardId = cardRepository.findCardIdByNumber("trips");
        Card card = cardRepository.findByUserEmail("bar@o2.pl");
        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip(1, "1.01", "2.01", 100, 101, card));
        trips.add(new Trip(2, "3.01", "4.01", 102, 103, card));
        trips.add(new Trip(3, "5.01", "6.01", 98, 99, card));
        trips.add(new Trip(4, "7.01", "8.01", 115, 116, card));
        trips.add(new Trip(5, "9.01", "10.01", 83, 84, card));
        trips.add(new Trip(6, "11.01", "12.01", 71, 72, card));
        tripRepository.saveAll(trips);
        List<Trip> sortedList = trips.stream()
                .sorted(Comparator.comparingInt(Trip::getTripStartVehicleCounter))
                .collect(Collectors.toList());
        Assertions.assertEquals(sortedList, tripRepository.findAllByCardIdOrderByTripStartVehicleCounter(cardId.getId()).orElse(null));
    }

    @Test
    void shouldGetAllTripsByCardId() {
        userRepository.save(new User(anyInt(), "angora@o2.pl", "foobar", "John", "Smith"));
        User user = userRepository.findByEmail("angora@o2.pl");
        cardRepository.save(new Card(2, "tripCard", "km", "12.12.1997", true, user));
        Card card = cardRepository.findByUserEmail("angora@o2.pl");
        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip(7, "1.01", "2.01", 100, 101, card));
        trips.add(new Trip(8, "1.01", "2.01", 102, 103, card));
        trips.add(new Trip(9, "1.01", "2.01", 98, 99, card));
        trips.add(new Trip(10, "1.01", "2.01", 115, 116, card));
        trips.add(new Trip(11, "1.01", "2.01", 83, 84, card));
        trips.add(new Trip(12, "1.01", "2.01", 71, 72, card));
        tripRepository.saveAll(trips);
        assertThat(tripRepository.findAllByCardId(2)).isNotNull();
    }

    @Test
    void shouldGetTripById() {
        userRepository.save(new User(anyInt(), "lincoln@o2.pl", "foobar", "John", "Smith"));
        User user = userRepository.findByEmail("lincoln@o2.pl");
        cardRepository.save(new Card(anyInt(), "trip", "zoo", "1.01.1997", true, user));
        Card card = cardRepository.findByUserEmail("lincoln@o2.pl");
        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip(13, "1.01", "2.01", 100, 101, card));
        trips.add(new Trip(14, "1.01", "2.01", 102, 103, card));
        trips.add(new Trip(15, "1.01", "2.01", 98, 99, card));
        trips.add(new Trip(16, "1.01", "2.01", 115, 116, card));
        trips.add(new Trip(17, "1.01", "2.01", 83, 84, card));
        trips.add(new Trip(18, "1.01", "2.01", 71, 72, card));
        tripRepository.saveAll(trips);
        Optional<Trip> result = Optional.ofNullable(trips.get(2));
        assertThat(tripRepository.findById(2).equals(result));
    }
}