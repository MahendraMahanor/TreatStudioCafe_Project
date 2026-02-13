package com.treatstudio.config;

import com.treatstudio.model.MenuItem;
import com.treatstudio.repository.MenuRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Adds sample menu items when the app starts, only if the menu is empty.
 * So you see a ready-made menu without running any commands.
 */
@Component
public class MenuDataSeeder implements ApplicationRunner {

    private final MenuRepository menuRepository;

    public MenuDataSeeder(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (menuRepository.count() > 0) {
            return; // already have items, don't add again
        }

        List<MenuItem> samples = List.of(
                new MenuItem(null, "Cappuccino", "Double shot, steamed milk", 120.0, "Coffee"),
                new MenuItem(null, "Croissant", "Buttery, fresh", 80.0, "Snacks"),
                new MenuItem(null, "Cold Coffee", "Iced with a scoop of vanilla", 130.0, "Coffee"),
                new MenuItem(null, "Veg Sandwich", "Fresh veggies and cheese", 100.0, "Snacks"),
                new MenuItem(null, "Chocolate Brownie", "Warm with a drizzle", 110.0, "Desserts")
        );

        menuRepository.saveAll(samples);
    }
}
