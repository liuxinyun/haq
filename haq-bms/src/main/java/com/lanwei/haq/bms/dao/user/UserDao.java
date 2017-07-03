package com.lanwei.haq.bms.dao.user;

import com.lanwei.haq.bms.dao.CommonDao;
import com.lanwei.haq.bms.entity.user.UserEntity;

import java.util.List;

/**
 * @author      liuxinyun
 * @date        2016年1月12日 下午2:48:21
 */
public interface UserDao extends CommonDao<UserEntity> {

    /**
     * 修改密码
     */
    public void updatePassword(UserEntity userEntity);

    /**
     * 批量新增用户
     * @param list
     */
    void addList(List<UserEntity> list);

}