package com.lanwei.haq.bms.controller.user;

import com.lanwei.haq.bms.entity.log.SysLogEntity;
import com.lanwei.haq.bms.entity.notice.NoticeEntity;
import com.lanwei.haq.bms.entity.role.RoleEntity;
import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.bms.service.log.SysLogService;
import com.lanwei.haq.bms.service.menu.MenuService;
import com.lanwei.haq.bms.service.role.RoleService;
import com.lanwei.haq.bms.service.user.UserService;
import com.lanwei.haq.bms.service.web.WebService;
import com.lanwei.haq.comm.annotation.AddEntity;
import com.lanwei.haq.comm.annotation.CurrentUser;
import com.lanwei.haq.comm.annotation.SysLog;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.enums.SexEnum;
import com.lanwei.haq.comm.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 用户管理Controller
 *
 * @author liuxinyun
 * @date 2016/12/19 22:30
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private final MenuService menuService;
    private final RoleService roleService;
    private final SysLogService sysLogService;
    private final WebService webService;

    @Autowired
    public UserController(UserService userService, MenuService menuService,
                          RoleService roleService, SysLogService sysLogService,
                          WebService webService) {
        this.userService = userService;
        this.menuService = menuService;
        this.roleService = roleService;
        this.sysLogService = sysLogService;
        this.webService = webService;
    }

    /**
     * 用户登录请求的地址
     *
     * @param userEntity
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(UserEntity userEntity, HttpServletRequest request) {
        Map<String, Object> resultMap;
        // 清空之前的session
        request.getSession().invalidate();
        userEntity = userService.login(userEntity);
        if (null == userEntity) {
            resultMap = ResponseEnum.PARAM_ERROR.getResultMap();
            resultMap.put("msg", "用户名或者密码错误");
        } else {
            resultMap = ResponseEnum.SUCCESS.getResultMap();
            request.getSession().setAttribute("user", userEntity);
            //获取请求ip
            String ip = request.getRemoteAddr();
            //获取请求路径
            String uri = request.getRequestURI();
            String param = userEntity.toString();
            SysLogEntity sysLogEntity = new SysLogEntity();
            sysLogEntity.setDescription("用户登录");
            sysLogEntity.setUrl(uri);
            sysLogEntity.setParam(param);
            sysLogEntity.setUserId(userEntity.getId());
            sysLogEntity.setUsername(userEntity.getName());
            sysLogEntity.setIp(ip);
            sysLogEntity.setCreater(userEntity.getId());
            sysLogEntity.setModifier(userEntity.getId());
            sysLogService.add(sysLogEntity);
        }
        return resultMap;
    }

    /**
     * 获取用户的基本信息
     *
     * @param userEntity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Map<String, Object> detail(@CurrentUser UserEntity userEntity) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        // 获取用户的当前信息
        resultMap.put("user", userEntity);
        ArrayList<NoticeEntity> notice = new ArrayList<>();
        NoticeEntity noticeEntity = new NoticeEntity("测试通知1");
        noticeEntity.setCreated(new Date());
        notice.add(noticeEntity);
        notice.add(noticeEntity);
        notice.add(noticeEntity);
        resultMap.put("notice", notice);

        // 查询用户的菜单
        resultMap.put("menu", menuService.getUserMenu(userEntity));
        //获取当前网站数量
        resultMap.put("webCount", webService.getWebCount());

        return resultMap;
    }

    /**
     * 新增用户
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SysLog(description = "新增用户")
    public Map<String, Object> insertUser(@AddEntity UserEntity userEntity){
        Map<String, Object> resultMap;
        if (userEntity.getRoleId()==0){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
            resultMap.put("msg","未选择用户类型");
            return resultMap;
        }
        userEntity.setHeadImage("/haq-bms/img/liuxinyun.jpg");
        resultMap = userService.insertUser(userEntity);
        return resultMap;
    }

    /**
     * 更新用户
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @SysLog(description = "更新用户")
    public Map<String, Object> updateUser(UserEntity userEntity, @CurrentUser UserEntity currentUser){
        Map<String, Object> resultMap;
        if(null == userEntity){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
            return resultMap;
        }
        userEntity.setModifier(currentUser.getId());
        resultMap = userService.updateUser(userEntity);
        return resultMap;
    }

    /**
     * 删除用户
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SysLog(description = "删除用户")
    public Map<String, Object> deleteUser(UserEntity userEntity, @CurrentUser UserEntity currentUser){
        Map<String, Object> resultMap;
        if(null == userEntity){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
        } else {
            resultMap = ResponseEnum.SUCCESS.getResultMap();
            userEntity.setModifier(currentUser.getId());
            userService.deleteUser(userEntity);
        }
        return resultMap;
    }

    /**
     * 修改密码
     */
    @ResponseBody
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @SysLog(description = "修改密码")
    public Map<String, Object> updatePassword(UserEntity userEntity, @CurrentUser UserEntity currentUser){
        Map<String, Object> resultMap;
        if(null == userEntity){
            resultMap = ResponseEnum.PARAM_NULL.getResultMap();
            return resultMap;
        }
        userEntity.setModifier(currentUser.getId());
        userService.updatePassword(userEntity);
        resultMap = ResponseEnum.SUCCESS.getResultMap();
        return resultMap;
    }

    /**
     * 查询用户列表
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map<String, Object> getAllUser(UserEntity userEntity){
        return userService.getAllUser(userEntity);
    }

    /**
     * 批量删除用户
     */
    @ResponseBody
    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    @SysLog(description = "批量删除用户")
    public Map<String, Object> delUser(int[] id, @CurrentUser UserEntity currentUser){
        return userService.deleteList(id, currentUser.getId());

    }

    /**
     * 通过id查询用户详情
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Map<String, Object> getUserById(@PathVariable("id") int id) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("user", userService.getUserById(id));
        return resultMap;
    }

    /**
     * 通过id查询用户详情用于编辑
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public Map<String, Object> getUserByIdForEdit(@PathVariable("id") int id) {
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        resultMap.put("user", userService.getUserById(id));
        resultMap.put("sex", SexEnum.getSex());
        RoleEntity roleEntity = new RoleEntity();
        resultMap.put("roleList", roleService.getRole(roleEntity));
        return resultMap;
    }

    /**
     * 查询所有机构，不分页
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Map<String, Object> get(){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        RoleEntity roleEntity = new RoleEntity();
        resultMap.put("roleList", roleService.getRole(roleEntity));
        return resultMap;
    }

}