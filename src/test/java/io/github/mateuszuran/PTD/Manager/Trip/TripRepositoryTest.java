package io.github.mateuszuran.PTD.Manager.Trip;

import io.github.mateuszuran.PTD.Manager.AppController;
import io.github.mateuszuran.PTD.Manager.Card.Card;
import io.github.mateuszuran.PTD.Manager.Card.CardRepository;
import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.manipulation.Ordering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.comparator.Comparators;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
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
        User user = userRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("User with given id not found"));
        cardRepository.save(new Card(1, "toggle", "author", "1.01.1997", true, user));
        Card card = cardRepository.findByUserId(1);
        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip(1, "1.01", "2.01", 100, 101, card));
        trips.add(new Trip(2, "1.01", "2.01", 102, 103, card));
        trips.add(new Trip(3, "1.01", "2.01", 98, 99, card));
        trips.add(new Trip(4, "1.01", "2.01", 115, 116, card));
        trips.add(new Trip(5, "1.01", "2.01", 83, 84, card));
        trips.add(new Trip(6, "1.01", "2.01", 71, 72, card));
        tripRepository.saveAll(trips);
        List<Trip> sortedList = trips.stream()
                .sorted(Comparator.comparingInt(Trip::getTripStartVehicleCounter))
                .collect(Collectors.toList());
        Assertions.assertEquals(sortedList, tripRepository.findAllByCardIdOrderByTripStartVehicleCounter(1));
    }

    @Test
    void shouldGetAllTripsByCardId() {
        User user = userRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("User with given id not found"));
        cardRepository.save(new Card(1, "toggle", "author", "1.01.1997", true, user));
        Card card = cardRepository.findByUserId(1);
        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip(1, "1.01", "2.01", 100, 101, card));
        trips.add(new Trip(2, "1.01", "2.01", 102, 103, card));
        trips.add(new Trip(3, "1.01", "2.01", 98, 99, card));
        trips.add(new Trip(4, "1.01", "2.01", 115, 116, card));
        trips.add(new Trip(5, "1.01", "2.01", 83, 84, card));
        trips.add(new Trip(6, "1.01", "2.01", 71, 72, card));
        tripRepository.saveAll(trips);
        assertThat(tripRepository.findAllByCardId(1)).isNotNull();
    }

    @Test
    void shouldGetTripById() {
        User user = userRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("User with given id not found"));
        cardRepository.save(new Card(1, "toggle", "author", "1.01.1997", true, user));
        Card card = cardRepository.findByUserId(1);
        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip(1, "1.01", "2.01", 100, 101, card));
        trips.add(new Trip(2, "1.01", "2.01", 102, 103, card));
        trips.add(new Trip(3, "1.01", "2.01", 98, 99, card));
        trips.add(new Trip(4, "1.01", "2.01", 115, 116, card));
        trips.add(new Trip(5, "1.01", "2.01", 83, 84, card));
        trips.add(new Trip(6, "1.01", "2.01", 71, 72, card));
        tripRepository.saveAll(trips);
        var result = trips.get(2);
        assertThat(tripRepository.findById(2).equals(result));
    }
}