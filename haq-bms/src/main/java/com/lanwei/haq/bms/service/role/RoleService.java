package com.lanwei.haq.bms.service.role;

import com.lanwei.haq.bms.dao.menu.MenuDao;
import com.lanwei.haq.bms.dao.role.RoleDao;
import com.lanwei.haq.bms.entity.role.RoleEntity;
import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.enums.RoleTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 权限组service
 *
 * @author liuxinyun
 * @date 2017/1/3 13:52
 */
@Service
public class RoleService {

    private final RoleDao roleDao;
    private final MenuDao menuDao;

    @Autowired
    public RoleService(RoleDao roleDao, MenuDao menuDao) {
        this.roleDao = roleDao;
        this.menuDao = menuDao;
    }

    /**
     * 查询权限组列表
     *
     * @param roleEntity
     * @return
     */
    public Map<String, Object> roleList(RoleEntity roleEntity) {
        //结果
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        int count = roleDao.count(roleEntity);
        if (count > 0) {
            resultMap.put("list", roleDao.query(roleEntity));
        }
        resultMap.put("count", count);
        return resultMap;
    }

    /**
     * 删除角色
     *
     * @param id
     * @param userId
     * @return
     */
    public Map<String, Object> delete(int[] id, int userId) {
        //
        roleDao.delList(id, userId);

        return ResponseEnum.SUCCESS.getResultMap();
    }

    /**
     * 查询末级级菜单用于设置权限拥有的菜单
     *
     * @param id
     * @return
     */
    public Map<String, Object> getRoleMenu(int id) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("roleId", id);
        resultMap.put("list", menuDao.getRoleMenu(id, "T"));
        return resultMap;
    }

    /**
     * 配置角色对应的菜单
     *
     * @param menuId
     * @param roleId
     * @param userEntity
     * @return
     */
    @Transactional
    public Map<String, Object> roleConfig(int[] menuId, int roleId, UserEntity userEntity) {

        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();

        // 先清除之前的角色菜单关系
        roleDao.delRel(roleId, userEntity.getId());

        if (null != menuId)
            roleDao.addRel(menuId, roleId, userEntity.getId());

        return resultMap;
    }

    /**
     * 新增权限
     *
     * @param roleEntity
     * @param userEntity
     * @return
     */
    public Map<String, Object> add(RoleEntity roleEntity, UserEntity userEntity) {

        ResponseEnum responseEnum;
        if (null == roleEntity) {
            responseEnum = ResponseEnum.PARAM_NULL;
        } else {
            roleEntity.setPidSeries(userEntity.getRoleId() + "");
            roleDao.add(roleEntity);
            responseEnum = ResponseEnum.SUCCESS;
        }
        return responseEnum.getResultMap();
    }

    /**
     * 查询角色不分页
     * @param roleEntity
     * @return
     */
    public List<RoleEntity> getRole(RoleEntity roleEntity){
        if (null == roleEntity){
            roleEntity = new RoleEntity();
        }
        // 不能创建开发人员的角色
        roleEntity.setType(RoleTypeEnum.DEV.getCode());
        roleEntity.setPageStart(0);
        roleEntity.setPageSize(Integer.MAX_VALUE);
        return roleDao.query(roleEntity);
    }
}