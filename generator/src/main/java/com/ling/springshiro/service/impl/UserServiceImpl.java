package com.ling.springshiro.service.impl;

import com.ling.springshiro.pojo.User;
import com.ling.springshiro.mapper.UserMapper;
import com.ling.springshiro.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ky2fe
 * @since 2021-10-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
