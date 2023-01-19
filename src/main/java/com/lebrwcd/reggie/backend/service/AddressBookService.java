package com.lebrwcd.reggie.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.reggie.backend.entity.AddressBook;
import com.lebrwcd.reggie.common.R;

import java.util.List;

/**
 * @author lebrwcd
 * @date 2023/1/19
 * @note
 */
public interface AddressBookService extends IService<AddressBook> {
    R<List<AddressBook>> listAddressBook();

    R<String> saveAddressBook(AddressBook addressBook);

    R<String> defaultAddress(AddressBook addressBook);
}
