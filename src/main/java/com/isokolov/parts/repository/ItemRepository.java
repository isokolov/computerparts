package com.isokolov.parts.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.isokolov.parts.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer>{

    //List<Item> findAllByNecessaryIsTrueOrderByAmount();
    List<Item> findAllByRequiredIsTrueOrderByAmount();

    Page<Item> findAllByOrderByName(Pageable pageable);

    //Page<Item> findAllByNecessaryIsFalseOrderByName(Pageable pageable);
    Page<Item> findAllByRequiredIsFalseOrderByName(Pageable pageable);

    //Page<Item> findAllByNecessaryIsTrueOrderByName(Pageable pageable);
    Page<Item> findAllByRequiredIsTrueOrderByName(Pageable pageable);

    Page<Item> findItemsByNameContains(Pageable pageable, String name);
}
