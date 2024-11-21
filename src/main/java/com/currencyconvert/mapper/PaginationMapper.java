package com.currencyconvert.mapper;

import org.springframework.data.domain.Page;

import com.example.currencyconvert.model.Pagination;

public final class PaginationMapper {

  private PaginationMapper() {
  }

  public static Pagination toPagination(Page<?> page) {
    final var pagination = new Pagination();

    pagination.setPageNumber(page.getNumber());
    pagination.setPageSize(page.getSize());
    pagination.setNumberOfItems(page.getNumberOfElements());
    pagination.setHasPrevious(page.hasPrevious());
    pagination.setHasNext(page.hasNext());
    pagination.setTotalItems(page.getTotalElements());
    pagination.setTotalPages(page.getTotalPages());

    return pagination;
  }

}
