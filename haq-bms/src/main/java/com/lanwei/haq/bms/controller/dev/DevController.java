package com.lanwei.haq.bms.controller.dev;

import com.lanwei.haq.bms.entity.menu.MenuEntity;
import com.lanwei.haq.bms.entity.role.RoleEntity;
import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.bms.service.dev.TableService;
import com.lanwei.haq.bms.service.menu.MenuService;
import com.lanwei.haq.bms.service.role.RoleService;
import com.lanwei.haq.comm.annotation.AddEntity;
import com.lanwei.haq.comm.annotation.CurrentUser;
import com.lanwei.haq.comm.annotation.SysLog;
import com.lanwei.haq.comm.enums.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 开发环境控制器，主要用于开发人员访问，可以设置一些系统属性，以及查看开发文档
 *
 * @author liuxinyun
 * @date 2016/12/21 19:04
 */
@RestController
@RequestMapping("/dev")
public class DevController {

    private final MenuService menuService;
    private final TableService tableService;
    private final RoleService roleService;

    @Autowired
    public DevController(MenuService menuService, TableService tableService, RoleService roleService) {
        this.menuService = menuService;
        this.tableService = tableService;
        this.roleService = roleService;
    }

    /**
     * 查询菜单
     *
     * @param menuEntity 菜单实体
     * @return
     */
    @RequestMapping("/menu/list")
    public Map<String, Object> menuList(MenuEntity menuEntity) {
        return menuService.menuList(menuEntity);
    }

    /**
     * 新增菜单
     *
     * @param menuEntity 菜单实体
     * @return
     */
    @RequestMapping(value = "/menu/add", method = RequestMethod.POST)
    @SysLog(description = "新增菜单")
    public Map<String, Object> addMenu(@AddEntity() MenuEntity menuEntity) {
        Map<String, Object> resultMap;
        if (null == menuEntity) {
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
        } else {
            resultMap = menuService.add(menuEntity);
        }
        return resultMap;
    }

    /**
     * 修改菜单
     *
     * @param menuEntity 菜单实体
     * @return
     */
    @RequestMapping(value = "/menu/update", method = RequestMethod.POST)
    @SysLog(description = "更新菜单")
    public Map<String, Object> updateMenu(@AddEntity MenuEntity menuEntity) {
        Map<String, Object> resultMap;
        if (null == menuEntity) {
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
        } else {
            resultMap = menuService.update(menuEntity);
        }
        return resultMap;
    }


    /**
     * 删除菜单
     *
     * @param id         需要删除的id数组
     * @param userEntity 当前用户
     * @return
     */
    @RequestMapping(value = "/menu/del", method = RequestMethod.POST)
    @SysLog(description = "删除菜单")
    public Map<String, Object> delMenu(int[] id, @CurrentUser UserEntity userEntity) {
        Map<String, Object> resultMap;
        if (null == id || id.length == 0) {
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
        } else {
            resultMap = menuService.delete(id, userEntity.getId());
        }
        return resultMap;
    }

    /**
     * 显示系统信息
     *
     * @return
     */
    @RequestMapping("/system/tables")
    public Map<String, Object> desc() {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        // 查询所有的表结构
        resultMap.put("list", tableService.desc());
        return resultMap;
    }

    /**
     * 查询权限列表
     *
     * @param roleEntity
     * @return
     */
    @RequestMapping("/role/list")
    public Map<String, Object> roleList(RoleEntity roleEntity) {
        return roleService.roleList(roleEntity);
    }

    /**
     * 新增权限组
     *
     * @param roleEntity
     * @return
     */
    @RequestMapping("/role/add")
    @SysLog(description = "新增权限组")
    public Map<String, Object> addRole(@AddEntity RoleEntity roleEntity, @CurrentUser UserEntity userEntity) {
        return roleService.add(roleEntity, userEntity);
    }


    /**
     * 删除菜单
     *
     * @param id         需要删除的id数组
     * @param userEntity 当前用户
     * @return
     */
    @RequestMapping(value = "/role/del", method = RequestMethod.POST)
    public Map<String, Object> delRole(int[] id, @CurrentUser UserEntity userEntity) {
        Map<String, Object> resultMap;
        if (null == id || id.length == 0) {
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
        } else {
            resultMap = roleService.delete(id, userEntity.getId());
        }
        return resultMap;
    }

    /**
     * 配置权限对应的菜单的时候需要用到
     *
     * @param id 权限id
     * @return
     */
    @RequestMapping("/role/menu")
    public Map<String, Object> getRoleMenu(int id) {
        return roleService.getRoleMenu(id);
    }

    /**
     * 设置菜单
     *
     * @param menuId 菜单id
     * @param roleId 角色id
     * @return
     */
    @RequestMapping("/role/config")
    @SysLog(description = "设置权限所属菜单")
    public Map<String, Object> roleConfig(int[] menuId, int roleId, @CurrentUser UserEntity userEntity) {
        return roleService.roleConfig(menuId, roleId, userEntity);
    }


}
