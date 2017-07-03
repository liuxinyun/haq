package com.lanwei.haq.bms.dao.menu;

import com.lanwei.haq.bms.dao.CommonDao;
import com.lanwei.haq.bms.entity.menu.MenuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单DAO
 *
 * @author liuxinyun
 * @date 2016/12/22 10:07
 */
public interface MenuDao extends CommonDao<MenuEntity> {

    /**
     * 查询通过groupId查询其所有的菜单
     *
     * @param roleId
     * @return
     */
    List<MenuEntity> getMenuByRoleId(int roleId);

    /**
     * 查询权限相关的菜单
     *
     * @param id
     * @param isLast
     * @return
     */
    List<MenuEntity> getRoleMenu(@Param("roleId") int id, @Param("isLast") String isLast);

    /**
     * 更新菜单
     * @param menuEntity
     */
    void updateIsLast(MenuEntity menuEntity);
}