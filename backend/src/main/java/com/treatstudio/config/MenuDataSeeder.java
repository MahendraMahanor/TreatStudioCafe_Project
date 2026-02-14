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
                // Coffee
                new MenuItem(null, "Cappuccino", "Double shot, steamed milk", 120.0, "Coffee"),
                new MenuItem(null, "Americano", "Espresso with hot water", 100.0, "Coffee"),
                new MenuItem(null, "Latte", "Smooth espresso with steamed milk", 130.0, "Coffee"),
                new MenuItem(null, "Cold Coffee", "Iced with a scoop of vanilla", 130.0, "Coffee"),
                new MenuItem(null, "Mocha", "Chocolate and espresso", 140.0, "Coffee"),
                new MenuItem(null, "Espresso", "Single or double shot", 80.0, "Coffee"),
                new MenuItem(null, "Filter Coffee", "South Indian style", 60.0, "Coffee"),
                new MenuItem(null, "Iced Latte", "Chilled latte over ice", 140.0, "Coffee"),
                // Cold Beverages
                new MenuItem(null, "Fresh Lime Soda", "Sweet or salt", 60.0, "Beverages"),
                new MenuItem(null, "Mango Shake", "Seasonal mango", 120.0, "Beverages"),
                new MenuItem(null, "Oreo Shake", "Blended Oreo with milk", 150.0, "Beverages"),
                new MenuItem(null, "Virgin Mojito", "Mint, lime, soda", 100.0, "Beverages"),
                new MenuItem(null, "Blue Lagoon", "Blue curacao, lemon, soda", 110.0, "Beverages"),
                new MenuItem(null, "Watermelon Juice", "Fresh watermelon", 90.0, "Beverages"),
                // Snacks
                new MenuItem(null, "Croissant", "Buttery, fresh", 80.0, "Snacks"),
                new MenuItem(null, "Veg Sandwich", "Fresh veggies and cheese", 100.0, "Snacks"),
                new MenuItem(null, "Grilled Sandwich", "Toasted with filling", 120.0, "Snacks"),
                new MenuItem(null, "French Fries", "Crispy golden fries", 90.0, "Snacks"),
                new MenuItem(null, "Nachos with Salsa", "Cheesy nachos", 130.0, "Snacks"),
                new MenuItem(null, "Paneer Tikka Sandwich", "Spiced paneer in bread", 130.0, "Snacks"),
                new MenuItem(null, "Maggi", "Classic or masala", 60.0, "Snacks"),
                new MenuItem(null, "Pav Bhaji", "Buttery pav with bhaji", 100.0, "Snacks"),
                // Main
                new MenuItem(null, "Pasta (White Sauce)", "Creamy veg pasta", 150.0, "Main"),
                new MenuItem(null, "Pasta (Red Sauce)", "Tomato basil veg pasta", 150.0, "Main"),
                new MenuItem(null, "Burger (Veg)", "Crispy patty with fries", 140.0, "Main"),
                new MenuItem(null, "Burger (Paneer)", "Spiced paneer patty with fries", 160.0, "Main"),
                new MenuItem(null, "Pizza (Margherita)", "Classic tomato and cheese", 200.0, "Main"),
                new MenuItem(null, "Pizza (Veg Loaded)", "Mixed vegetables", 220.0, "Main"),
                new MenuItem(null, "Wrap (Veg)", "Fresh wrap with filling", 130.0, "Main"),
                new MenuItem(null, "Wrap (Paneer)", "Grilled paneer wrap", 150.0, "Main"),
                // Desserts
                new MenuItem(null, "Chocolate Brownie", "Warm with a drizzle", 110.0, "Desserts"),
                new MenuItem(null, "Chocolate Pastry", "Rich chocolate slice", 100.0, "Desserts"),
                new MenuItem(null, "Blueberry Cheesecake", "Creamy slice", 150.0, "Desserts"),
                new MenuItem(null, "Ice Cream (Scoop)", "Vanilla, chocolate, strawberry", 80.0, "Desserts"),
                new MenuItem(null, "Waffle with Ice Cream", "Belgian waffle", 160.0, "Desserts"),
                new MenuItem(null, "Gulab Jamun", "2 pieces", 60.0, "Desserts")
        );

        menuRepository.saveAll(samples);
    }
}
