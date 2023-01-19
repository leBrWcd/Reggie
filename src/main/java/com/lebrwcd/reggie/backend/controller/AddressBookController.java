package com.lebrwcd.reggie.backend.controller;/**
 * @author lebrwcd
 * @date 2023/1/19
 * @note
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lebrwcd.reggie.backend.entity.AddressBook;
import com.lebrwcd.reggie.backend.service.AddressBookService;
import com.lebrwcd.reggie.common.R;
import com.lebrwcd.reggie.common.util.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName AddressBookController
 * Description 地址控制器
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/19
 */
@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 列表查询
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> listAddressBook() {
        return addressBookService.listAddressBook();
    }

    /**
     * 新增地址
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<String> saveAddressBook(@RequestBody AddressBook addressBook) {
        return addressBookService.saveAddressBook(addressBook);
    }


    /**
     * 设置默认地址
     */
    @PutMapping("default")
    public R<String> setDefault(@RequestBody AddressBook addressBook) {
        log.info("addressBook:{}", addressBook);
        return addressBookService.defaultAddress(addressBook);
    }

    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        } else {
            return R.error("没有找到该对象");
        }
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public R<AddressBook> getDefault() {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        //SQL:select * from address_book where user_id = ? and is_default = 1
        queryWrapper.eq(AddressBook::getUserId, BaseContext.get());
        queryWrapper.eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        if (null == addressBook) {
            return R.error("没有找到该对象");
        } else {
            return R.success(addressBook);
        }
    }
}
