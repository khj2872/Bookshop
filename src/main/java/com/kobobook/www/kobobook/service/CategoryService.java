package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.dto.CategoryDTO;
import com.kobobook.www.kobobook.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    @Transactional
    @Cacheable(value = "categoryListCache")
    public List<CategoryDTO> readCategoryList() {
        return categoryRepository.findAll().stream()
                .map(c -> modelMapper.map(c, CategoryDTO.class))
                .collect(Collectors.toList());
    }
}
