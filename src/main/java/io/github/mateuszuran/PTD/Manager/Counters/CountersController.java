package io.github.mateuszuran.PTD.Manager.Counters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/card")
@Controller
public class CountersController {
    private static final Logger logger = LoggerFactory.getLogger(CountersController.class);
    private final CounterService counterService;

    public CountersController(final CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping("/counters/update/{id}")
    public String updateCounters(@PathVariable("id") Integer id, Model model) {
        counterService.saveCounters(id);
        return "redirect:/card/" + id;
    }
}
