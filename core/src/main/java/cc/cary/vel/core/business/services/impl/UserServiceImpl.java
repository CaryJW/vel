package cc.cary.vel.core.business.services.impl;

import cc.cary.vel.core.business.entities.User;
import cc.cary.vel.core.business.mapper.UserMapper;
import cc.cary.vel.core.business.services.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author Cary
 * @date 2021/05/22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
