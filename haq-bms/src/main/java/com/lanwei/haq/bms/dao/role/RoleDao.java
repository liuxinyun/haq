package com.lanwei.haq.bms.dao.role;

import com.lanwei.haq.bms.dao.CommonDao;
import com.lanwei.haq.bms.entity.role.RoleEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 角色信息管理
 *
 * @author liuxinyun
 * @date
 */
public interface RoleDao extends CommonDao<RoleEntity> {

    /**
     * 清除角色的菜单信息
     *
     * @param roleId
     * @param userId
     */
    void delRel(@Param("roleId") int roleId, @Param("userId") int userId);

    /**
     * 添加角色的菜单关系
     *
     * @param menuId
     * @param roleId
     * @param userId
     */
    void addRel(@Param("menuId") int[] menuId, @Param("roleId") int roleId, @Param("userId") int userId);
}