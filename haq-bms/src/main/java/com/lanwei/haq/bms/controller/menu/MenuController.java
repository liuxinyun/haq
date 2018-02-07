package com.lanwei.haq.bms.controller.menu;

import com.lanwei.haq.bms.entity.menu.MenuEntity;
import com.lanwei.haq.bms.service.menu.MenuService;
import com.lanwei.haq.comm.enums.MenuEnum;
import com.lanwei.haq.comm.enums.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 菜单
 *
 * @author liuxinyun
 * @date 2016/12/21 19:04
 */
@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 通过查询条件查询菜单，不分页
     *
     * @param menuEntity
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Map<String, Object> getMenu(MenuEntity menuEntity) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("list", menuService.getMenu(menuEntity));
        return resultMap;
    }

    /**
     * 通过id查询菜单详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Map<String, Object> getMenuById(@PathVariable("id") int id) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("menu", menuService.getMenuById(id));
        // 查询所有菜单类型为模块的数据
        resultMap.put("list", menuService.getMenu(new MenuEntity(MenuEnum.MODULE.getCode())));
        resultMap.put("list1", menuService.getMenu(new MenuEntity(MenuEnum.MENU.getCode())));
        return resultMap;
    }

}