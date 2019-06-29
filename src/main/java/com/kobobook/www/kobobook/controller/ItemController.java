package com.kobobook.www.kobobook.controller;

import com.kobobook.www.kobobook.domain.Category;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.repository.CategoryRepository;
import com.kobobook.www.kobobook.repository.ItemRepository;
import com.kobobook.www.kobobook.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/register")
    public String createForm(Model model) {
        List<Category> categoryList = categoryRepository.findAll();

        model.addAttribute("categoryList", categoryList);

        return "item/register";
    }

    @PostMapping("/register")
    public String create(Item item) {
        System.out.println("item : " + item);
        itemService.create(item);
        return "redirect:/admin/item/register";

    }

    @GetMapping("/list")
    public String list(/*@RequestParam("pageNo") Integer pageNo, */Model model) {
        Page<Item> itemList = itemRepository.findAll(PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "id")));

//        Map<String, Object> pageInfo = new HashMap<>();
//        pageInfo.put("totalElements", itemList.getTotalElements());
//        pageInfo.put("totalPages", itemList.getTotalElements());
//        pageInfo.put("number", itemList.getNumber());
//        pageInfo.put("isFist", itemList.isFirst());
//        pageInfo.put("isLast", itemList.isLast());
//
//        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("itemList", itemList.getContent());
        return "item/list";
    }


}
