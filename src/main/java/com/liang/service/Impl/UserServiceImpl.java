package com.liang.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.dto.LoginFormDTO;
import com.liang.dto.Result;
import com.liang.dto.UserDTO;
import com.liang.entity.User;
import com.liang.mapper.UserMapper;
import com.liang.service.IUserService;
import com.liang.utils.RegexUtils;
import com.liang.utils.SystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import cn.hutool.core.lang.UUID;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.liang.utils.RedisConstants.*;
import static com.liang.utils.SystemConstants.USER_NICK_NAME_PREFIX;
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result login(LoginFormDTO loginFormDTO) {
        String phone=loginFormDTO.getPhone();
        if(RegexUtils.isPhoneInvalid(phone) ){
            return Result.fail("手机号格式不正确");
        }
        String Cachecode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        String code=loginFormDTO.getCode();
        if(Cachecode==null || !Cachecode.equals(code) ){
            return Result.fail("验证码错误");
        }
        // 4.一致，根据手机号查询用户 select * from tb_user where phone = ?
        User user = query().eq("phone", phone).one();//mybatis-plus的用法把
        if(user==null){
            user=createUserWithPhone(phone);
        }
        // 7.1.随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString(true);
        UserDTO userDTO=BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> userMap =BeanUtil.beanToMap(userDTO,new HashMap<>(), CopyOptions.create()
                .setIgnoreNullValue(true)
                .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        String tokenKey=LOGIN_USER_KEY+token;
        stringRedisTemplate.opsForHash().putAll(tokenKey,userMap);
        stringRedisTemplate.expire(tokenKey,LOGIN_USER_TTL, TimeUnit.SECONDS);
        return Result.ok();
    }

    private User createUserWithPhone(String phone) {
        User user=new User();
        user.setPhone(phone);
        user.setNickName(USER_NICK_NAME_PREFIX+RandomUtil.randomString(10));
        save(user);
        return user;
    }


    @Override
    public Result sendCode(String phone) {
        if(RegexUtils.isPhoneInvalid(phone)){
            return Result.fail("请输入正确的手机号");
        }
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY+phone,code);
        // 5.发送验证码
        log.debug("发送短信验证码成功，验证码：{}", code);

        return Result.ok();
    }
}
