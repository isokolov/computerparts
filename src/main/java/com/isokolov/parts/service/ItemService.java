package com.isokolov.parts.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.isokolov.parts.entity.Item;

import java.util.List;

public interface ItemService {

    Item getItemById(Integer id);
    void saveItem(Item item);
    void updateItem(Integer id, String name, Integer amount, boolean isRequired);
    void deleteItem(Integer id);

    Page<Item> findAllByName(Pageable pageable);
    Page<Item> findAllByRequiredFalse(Pageable pageable);
    Page<Item> findAllByRequiredTrue(Pageable pageable);
    Page<Item> findItemByName(Pageable pageable, String name);

    List<Item> findAllByRequiredIsTrueSorted();
}
