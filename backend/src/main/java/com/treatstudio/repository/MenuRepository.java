package com.treatstudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.treatstudio.model.MenuItem;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByCategoryIgnoreCase(String category);
}
