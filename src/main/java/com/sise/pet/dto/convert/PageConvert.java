package com.sise.pet.dto.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageConvert<D, E> {

    public Page<D> toPageDto(Page<E> entityPage, List<D> records){
        Page<D> dtoPage = new Page<>();
        dtoPage.setCurrent(entityPage.getCurrent());
        dtoPage.setRecords(records);
        dtoPage.setSize(entityPage.getSize());
        dtoPage.setPages(entityPage.getPages());
        dtoPage.setOrders(entityPage.getOrders());
        dtoPage.setTotal(entityPage.getTotal());
        return dtoPage;
    }


    public Page<E> toEntity(Page<D> dtoPage){
        return null;
    }
}
