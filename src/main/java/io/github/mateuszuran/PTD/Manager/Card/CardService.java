package io.github.mateuszuran.PTD.Manager.Card;

import io.github.mateuszuran.PTD.Manager.Counters.CounterService;
import io.github.mateuszuran.PTD.Manager.Counters.Counters;
import io.github.mateuszuran.PTD.Manager.Fuel.Fuel;
import io.github.mateuszuran.PTD.Manager.Fuel.FuelService;
import io.github.mateuszuran.PTD.Manager.Trip.Trip;
import io.github.mateuszuran.PTD.Manager.Trip.TripService;
import io.github.mateuszuran.PTD.Manager.Vehicle.Vehicle;
import io.github.mateuszuran.PTD.Manager.Vehicle.VehicleService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final VehicleService vehicleService;
    private final FuelService fuelService;
    private final CounterService counterService;
    private final TripService tripService;

    public CardService(final CardRepository cardRepository, final VehicleService vehicleService, final FuelService fuelService,
                       final CounterService counterService, final TripService tripService) {
        this.cardRepository = cardRepository;
        this.vehicleService = vehicleService;
        this.fuelService = fuelService;
        this.counterService = counterService;
        this.tripService = tripService;
    }

    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    public boolean checkIfCardExists(Card card) {
        return cardRepository.existsByNumber(card.getNumber());
    }

    public List<Card> findAllCards() {
        return cardRepository.findAll();
    }

    public List<Card> findAllByUserId(Integer id) {
        return cardRepository.findAllCardsByUserId(id);
    }

    public Card findByUserId(Integer id) {
        return cardRepository.findByUserId(id);
    }

    public void deleteCard(Integer id) {
        cardRepository.deleteById(id);
    }

    public boolean hasRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("Admin"));
    }

    public String currentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public void toggleCard(Integer id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Card of given id not exists"));
        card.setDone(!card.isDone());
        cardRepository.save(card);
    }

    public Card findCard(Integer id) {
        return cardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Card of given id not exists"));
    }

    public boolean checkIfCardIsDone(Integer id) {
        Card card = cardRepository.findByUserId(id);
        return card.isDone();
    }

    public Card getAllDataFromCard(Integer cardId, Integer userId) {
        Card card = new Card();
        Card cardFromDb = findCard(cardId);
        card.setNumber(cardFromDb.getNumber());
        card.setDone(cardFromDb.isDone());
        Vehicle vehicle = vehicleService.findVehicleByUserId(userId);
        card.setVehicle(vehicle);
        List<Fuel> fuel = fuelService.findAllAndSort(cardId);
        card.setFuel(fuel);
        Counters counters = counterService.findByCardId(cardId);
        card.setCounters(counters);
        List<Trip> trip = tripService.findAllAndSort(cardId);
        card.setTrip(trip);

        return card;
    }

    public boolean checkCardUser(Integer id, Integer userId) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Card of given id not exists"));
        Integer cardUserId = card.getUser().getId();
        return cardUserId.equals(userId);
    }
}
