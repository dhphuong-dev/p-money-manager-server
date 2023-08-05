package com.example.moneymanagerbe.controller;

import com.example.moneymanagerbe.base.RestApiV1;
import com.example.moneymanagerbe.base.VsResponseUtil;
import com.example.moneymanagerbe.constant.UrlConstant;
import com.example.moneymanagerbe.domain.dto.request.CategoryRequestDto;
import com.example.moneymanagerbe.security.CurrentUser;
import com.example.moneymanagerbe.security.UserPrincipal;
import com.example.moneymanagerbe.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class CategoryController {

    private final CategoryService categoryService;

    @Tag(name = "category-controller")
    @Operation(summary = "API get categories by current user")
    @GetMapping(UrlConstant.Category.GET_CATEGORIES)
    public ResponseEntity<?> getTransactionsByUser(@Parameter(name = "user", hidden = true)
                                                       @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(categoryService.getCategoriesDtoByUser(user.getId()));
    }

    @Tag(name = "category-controller")
    @Operation(summary = "API create new category")
    @PostMapping(value = UrlConstant.Category.POST_NEW_CATEGORY, consumes = "multipart/form-data")
    public ResponseEntity<?> createNewCategory(@Valid @ModelAttribute CategoryRequestDto categoryRequestDto,
                                               @Parameter(name = "user", hidden = true)
                                               @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(categoryService.createNew(user.getId(), categoryRequestDto));
    }

    @Tag(name = "category-controller")
    @Operation(summary = "API delete category by id")
    @DeleteMapping(UrlConstant.Category.DELETE_CATEGORY)
    public ResponseEntity<?> deleteCategory(@PathVariable String id,
                                            @Parameter(name = "user", hidden = true)
                                            @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(categoryService.delete(id, user.getId()));
    }

}
