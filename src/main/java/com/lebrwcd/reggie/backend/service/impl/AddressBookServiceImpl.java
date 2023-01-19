package com.lebrwcd.reggie.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.entity.AddressBook;
import com.lebrwcd.reggie.backend.entity.User;
import com.lebrwcd.reggie.backend.mapper.AddressBookMapper;
import com.lebrwcd.reggie.backend.service.AddressBookService;
import com.lebrwcd.reggie.common.R;
import com.lebrwcd.reggie.common.util.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName AddressBookServiceImpl
 * Description 地址篇业务实现
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/19
 */
@Service
@Slf4j
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    @Override
    public R<List<AddressBook>> listAddressBook() {
        // 获取移动端当前登录用户id
        Long userId = BaseContext.get();
        // 根据当前用户查询地址列表
        List<AddressBook> list = lambdaQuery().eq(AddressBook::getUserId, userId).list();
        return R.success(list);
    }

    @Override
    public R<String> saveAddressBook(AddressBook addressBook) {
        // 1.获取移动端当前登录用户id
        Long userId = BaseContext.get();
        // 设置地址蒲的用户id
        addressBook.setUserId(userId);
        baseMapper.insert(addressBook);
        return R.success("新增地址成功！");
    }

    @Override
    public R<String> defaultAddress(AddressBook addressBook) {

        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.get());
        wrapper.set(AddressBook::getIsDefault, 0);
        //SQL:update address_book set is_default = 0 where user_id = ?
        baseMapper.update(addressBook,wrapper);

        addressBook.setIsDefault(1);
        //SQL:update address_book set is_default = 1 where id = ?
        baseMapper.updateById(addressBook);
        return R.success("设置默认地址成功!");
    }
}
