package com.isokolov.parts.service;

import com.isokolov.parts.entity.Item;
import com.isokolov.parts.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository repository;

    @Autowired
    public void setNoteRepository(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public Item getItemById(Integer id) {
        return this.repository.getOne(id);
    }

    @Override
    public void saveItem(Item item) {
        this.repository.save(item);
    }

    @Override
    public void updateItem(Integer id, String name, Integer amount, boolean isRequired) {
        Item updated = this.repository.getOne(id);
        updated.setRequired(isRequired);
        updated.setName(name);
        updated.setAmount(amount);
        this.repository.save(updated);
    }

    @Override
    public void deleteItem(Integer id) {
        this.repository.delete(id);
    }

    @Override
    public List<Item> findAllByRequiredIsTrueSorted() {
        return this.repository.findAllByRequiredIsTrueOrderByAmount();
    }
    @Override
    public Page<Item> findAllByName(Pageable pageable) {
        return this.repository.findAllByOrderByName(pageable);
    }

    @Override
    public Page<Item> findAllByRequiredFalse(Pageable pageable) {
        return this.repository.findAllByRequiredIsFalseOrderByName(pageable);
    }

    @Override
    public Page<Item> findAllByRequiredTrue(Pageable pageable) {
        return this.repository.findAllByRequiredIsTrueOrderByName(pageable);
    }

    @Override
    public Page<Item> findItemByName(Pageable pageable, String name) {
        return this.repository.findItemsByNameContains(pageable, name);
    }
}
