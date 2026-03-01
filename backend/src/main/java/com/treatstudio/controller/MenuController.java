package com.treatstudio.controller;

import com.treatstudio.model.MenuItem;
import com.treatstudio.repository.MenuRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000", "https://mahendramahanor.github.io", "null"}, allowCredentials = "true")
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuRepository repository;

    public MenuController(MenuRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<MenuItem> getMenu(@RequestParam(required = false) String category) {
        if (category != null && !category.isBlank()) {
            return repository.findByCategoryIgnoreCase(category.trim());
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public MenuItem getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Menu item not found: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItem addMenu(@Valid @RequestBody MenuItem item) {
        return repository.save(item);
    }

    @PutMapping("/{id}")
    public MenuItem updateMenu(@PathVariable Long id, @Valid @RequestBody MenuItem item) {
        MenuItem existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Menu item not found: " + id));
        existing.setName(item.getName());
        existing.setDescription(item.getDescription());
        existing.setPrice(item.getPrice());
        existing.setCategory(item.getCategory());
        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Menu item not found: " + id);
        }
        repository.deleteById(id);
    }
}
