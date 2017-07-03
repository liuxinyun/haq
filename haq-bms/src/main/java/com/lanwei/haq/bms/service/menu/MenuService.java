package com.lanwei.haq.bms.service.menu;

import com.lanwei.haq.bms.dao.menu.MenuDao;
import com.lanwei.haq.bms.entity.menu.MenuEntity;
import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.comm.enums.MenuEnum;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.util.ListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单信息管理
 *
 * @author liuxinyun
 * @date 2016/12/25 19:04
 */
@Service
public class MenuService {

    private final static Logger log = LoggerFactory.getLogger(MenuService.class);

    private final MenuDao menuDao;

    @Autowired
    public MenuService(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    /**
     * 新增菜单实体
     *
     * @param menuEntity
     * @return
     */
    public Map<String, Object> add(MenuEntity menuEntity) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();

        boolean isMenu = false;
        boolean isItem = false;
        // 菜单项
        if (menuEntity.getMenuId() != 0) {
            // 查询该菜单的父菜单
            MenuEntity param = new MenuEntity();
            param.setId(menuEntity.getMenuId());
            param.setIsLast("F");
            menuDao.updateIsLast(param);
            List<MenuEntity> list = menuDao.query(param);
            //查询该菜单的模块
            MenuEntity param1 = new MenuEntity();
            param1.setId(menuEntity.getModuleId());
            List<MenuEntity> list1 = menuDao.query(param1);
            if (ListUtil.isNotEmpty(list)) {
                menuEntity.setMenuName(list.get(0).getName());
                menuEntity.setModuleName(list1.get(0).getName());
                menuEntity.setIsLast("T");
                menuEntity.setType(MenuEnum.ITEM.getCode());
                isItem = true;
            }
        }

        //菜单
        if (!isItem && menuEntity.getModuleId() != 0) {
            // 查询该菜单的模块
            MenuEntity param = new MenuEntity();
            param.setId(menuEntity.getModuleId());
            param.setIsLast("F");
            menuDao.updateIsLast(param);
            List<MenuEntity> list = menuDao.query(param);
            if (ListUtil.isNotEmpty(list)) {
                menuEntity.setMenuName("");
                menuEntity.setModuleName(list.get(0).getName());
                menuEntity.setType(MenuEnum.MENU.getCode());
                menuEntity.setIsLast("T");
                isMenu = true;
            }
        }

        // 模块
        if (!isMenu && !isItem) {
            menuEntity.setMenuName("");
            menuEntity.setModuleName("");
            menuEntity.setModuleId(0);
            menuEntity.setIsLast("F");
            menuEntity.setType(MenuEnum.MODULE.getCode());
        }

        menuDao.add(menuEntity);
        return resultMap;
    }

    /**
     * 查询所有的菜单列表,带分页
     *
     * @param menuEntity
     * @return
     */
    public Map<String, Object> menuList(MenuEntity menuEntity) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();

        // 查询总数
        int count = menuDao.count(menuEntity);
        menuEntity.setCount(count);
        // 没有查询到则不执行下面的查询
        if (count > 0) {
            List<MenuEntity> list = menuDao.query(menuEntity);

            for(MenuEntity menu:list){
                 menu.setTypeStr(MenuEnum.getMenuEnum(menu.getType()).getDesc());
            }

            resultMap.put("list", list);
        }

        resultMap.put("count", count);
        resultMap.put("queryEntity", menuEntity);
        return resultMap;
    }

    /**
     * 查询用户的菜单
     *
     * @param userEntity
     * @return
     */
    public List<MenuEntity> getUserMenu(UserEntity userEntity) {
        List<MenuEntity> menuList = new ArrayList<>();
        if (ListUtil.isEmpty(menuList)) {
            menuList = menuDao.getMenuByRoleId(userEntity.getRoleId());
        }
        return menuList;
    }

    /**
     * 查询菜单，不分页
     *
     * @param menuEntity
     * @return
     */
    public List<MenuEntity> getMenu(MenuEntity menuEntity) {
        if (null == menuEntity) {
            menuEntity = new MenuEntity();
        }
        menuEntity.setPageStart(0);
        menuEntity.setPageSize(Integer.MAX_VALUE);
        return menuDao.query(menuEntity);
    }

    /**
     * 通过id查询菜单
     *
     * @param id
     * @return
     */
    public MenuEntity getMenuById(int id) {

        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setId(id);

        List<MenuEntity> list = menuDao.query(menuEntity);

        return ListUtil.isNotEmpty(list) ? list.get(0) : null;

    }

    /**
     * 删除菜单
     *
     * @param id
     * @param userId
     * @return
     */
    public Map<String, Object> delete(int[] id, int userId) {
        //
        menuDao.delList(id, userId);

        return ResponseEnum.SUCCESS.getResultMap();
    }

    /**
     * 修改菜单
     *
     * @param menuEntity
     * @return
     */
    public Map<String, Object> update(MenuEntity menuEntity) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();

        boolean isMenu = false;
        boolean isItem = false;
        // 菜单
        if (menuEntity.getModuleId() != 0 && menuEntity.getMenuId() != 0 && menuEntity.getMenuId() != menuEntity.getId()) {
            //查询该菜单的模块
            MenuEntity param1 = new MenuEntity();
            param1.setId(menuEntity.getModuleId());
            List<MenuEntity> list1 = menuDao.query(param1);
            // 查询该菜单的父菜单
            MenuEntity param = new MenuEntity();
            param.setId(menuEntity.getMenuId());
            List<MenuEntity> list = menuDao.query(param);
            if (ListUtil.isNotEmpty(list)) {
                menuEntity.setModuleName(list1.get(0).getName());
                menuEntity.setMenuName(list.get(0).getName());
                menuEntity.setType(MenuEnum.ITEM.getCode());
                isItem = true;
            }
        }

        //项
        if (!isItem && menuEntity.getModuleId() != 0 && menuEntity.getModuleId() != menuEntity.getId()) {
            // 查询该菜单的模块
            MenuEntity param = new MenuEntity();
            param.setId(menuEntity.getModuleId());
            List<MenuEntity> list = menuDao.query(param);
            if (ListUtil.isNotEmpty(list)) {
                menuEntity.setModuleName(list.get(0).getName());
                menuEntity.setMenuName("");
                menuEntity.setType(MenuEnum.MENU.getCode());
                isMenu = true;
            }
        }

        // 模块
        if (!isMenu && !isItem) {
            menuEntity.setMenuName("");
            menuEntity.setModuleName("");
            menuEntity.setModuleId(menuEntity.getId());
            menuEntity.setType(MenuEnum.MODULE.getCode());
        }

        menuDao.update(menuEntity);
        return resultMap;
    }
}