package com.isokolov.parts.controller;

import com.isokolov.parts.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import com.isokolov.parts.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ItemController {

    private ItemService service;

    private int counter = 0;
    private boolean activator = false;
    private String findString = "";

    @Autowired
    public void setItemService(ItemService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String list(Model model, @PageableDefault(size = 10) Pageable pageable) {
        int amount = getQuantity();
        Page<Item> pages = setPagesUp(pageable);

        model.addAttribute("counter", counter);
        model.addAttribute("amount", amount);

        model.addAttribute("number", pages.getNumber());
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("totalElements", pages.getTotalElements());
        model.addAttribute("items", pages.getContent());

        return "index";
    }

    @GetMapping("/sort")
    public String sortChoose() {
        this.activator = true;
        return "redirect:/";
    }

    @GetMapping("/new")
    public String addItem() {
        return "operations/new";
    }

    @PostMapping("/add")
    public String addItem(@RequestParam String name,
                          @RequestParam Integer amount,
                          @RequestParam(value = "required", required = false) boolean isRequired) {
        Item item = new Item(name, isRequired, amount);
        this.service.saveItem(item);

        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchItem() {
        return "operations/search";
    }

    @PostMapping("/find")
    public String findName(
            @RequestParam String name
    ) {
        this.counter = 3;
        this.findString = name;
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Integer id,
            Model model
    ) {
        Item item = this.service.getItemById(id);
        model.addAttribute("item", item);
        return "operations/edit";
    }

    @PostMapping("/update")
    public String saveItem(
            @RequestParam Integer id,
            @RequestParam String name,
            @RequestParam Integer amount,
            @RequestParam(value = "required", required = false) boolean isRequired
    ) {
        this.service.updateItem(id, name, amount, isRequired);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        this.service.deleteItem(id);
        return "redirect:/";
    }

    @GetMapping("/index")
    public String reset() {
        this.counter = 0;
        this.activator = false;
        return "redirect:/";
    }

    @GetMapping("/list")
    public String userList(Model model, Pageable pageable) {
        Page<Item> pages = service.findAllByName(pageable);
        model.addAttribute("number", pages.getNumber());
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("totalElements", pages.getTotalElements());
        model.addAttribute("size", pages.getSize());
        model.addAttribute("items", pages.getContent());
        return "/user/list";
    }

    private Page<Item> setPagesUp(Pageable pageable) {
        Page<Item> pages = null;

        if (activator) {
            this.counter = this.counter < 2 ? ++this.counter : 0;
            this.activator = false;
        }

        switch (this.counter) {
            case 0 :
                pages = this.service.findAllByName(pageable);
                break;
            case 1 :
                pages = this.service.findAllByRequiredTrue(pageable);
                break;
            case 2 :
                pages = this.service.findAllByRequiredFalse(pageable);
                break;
            case 3 :
                pages = this.service.findItemByName(pageable, findString);
                break;
        }
        return pages;
    }

    private int getQuantity() {
        List<Item> items = this.service.findAllByRequiredIsTrueSorted();
        return items == null || items.size() == 0 ? 0 : items.get(0).getAmount();
    }
}

