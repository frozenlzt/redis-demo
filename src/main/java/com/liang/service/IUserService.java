package com.liang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.dto.LoginFormDTO;
import com.liang.dto.Result;
import com.liang.entity.User;

public interface IUserService extends IService<User> {

    Result login(LoginFormDTO loginFormDTO);
    Result sendCode(String phone);
}
