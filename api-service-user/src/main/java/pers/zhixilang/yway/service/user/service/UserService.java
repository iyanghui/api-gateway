package pers.zhixilang.yway.service.user.service;

import org.springframework.stereotype.Service;
import pers.zhixilang.yway.service.user.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-18 10:59
 */
@Service
public class UserService {

    public List<UserEntity> getUsers() {
        List<UserEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserEntity entity = new UserEntity();
            entity.setId((long) i);
            entity.setName("hello" + i);
            entity.setAge(i);
            list.add(entity);
        }
        return list;
    }
}
