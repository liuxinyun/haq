package com.lanwei.haq.bms.service.user;

import com.lanwei.haq.bms.dao.user.UserDao;
import com.lanwei.haq.bms.entity.user.UserEntity;
import com.lanwei.haq.comm.enums.ResponseEnum;
import com.lanwei.haq.comm.enums.RoleTypeEnum;
import com.lanwei.haq.comm.util.ListUtil;
import com.lanwei.haq.comm.util.MD5Util;
import com.lanwei.haq.comm.util.PatternUtil;
import com.lanwei.haq.comm.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户service,处理用户相关的业务逻辑
 *
 * @author liuxinyun
 * @date 2016/12/31 14:42
 */
@Service
public class UserService {

    private final UserDao userDao;

    @Value("${md5.prefix}")
    private String md5Prefix;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 登录的时候查询用户是否登录正确
     *
     * @param userEntity
     * @return
     */
    public UserEntity login(UserEntity userEntity) {
        // 判断登录用户
        if (null != userEntity && StringUtils.isNotBlank(userEntity.getEmail()) && StringUtils.isNotBlank(userEntity.getPassword())) {
            // 目前用户名为邮箱
            UserEntity paramUser = new UserEntity();
            paramUser.setEmail(userEntity.getEmail());
            List<UserEntity> list = userDao.query(paramUser);
            if (ListUtil.isNotEmpty(list)) {
                UserEntity user = list.get(0);
                String md5Str = MD5Util.MD5(md5Prefix + userEntity.getPassword());
                // 判断密码是否相等
                if (user.getPassword().equals(md5Str)) {
                    return user;
                }
            }
        }
        return null;
    }

    /**
     * 获取用户列表
     */
    public Map<String, Object> getAllUser(UserEntity userEntity){
        Map<String, Object> resultMap = ResponseEnum.SUCCESS.getResultMap();
        if(null == userEntity) {
            userEntity = new UserEntity();
        }

        // 不允许查看开发人员
        userEntity.setRoleType(RoleTypeEnum.DEV.getCode());

        int count = userDao.count(userEntity);
        if(count > 0){
            resultMap.put("list", userDao.query(userEntity));
        }
        resultMap.put("count", count);
        resultMap.put("queryEntity", userEntity);
        return resultMap;
    }

    /**
     * 根据id获得用户
     */
    public UserEntity getUserById(int id){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        List<UserEntity> list = userDao.query(userEntity);
        if(ListUtil.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增用户
     */
    public Map<String, Object> insertUser(UserEntity userEntity){
        Map<String, Object> resultMap;
        String email = userEntity.getEmail();
        if (StringUtils.isBlank(email) || !PatternUtil.isEmail(email)){
            resultMap = ResponseEnum.PARAM_ERROR.getResultMap();
            resultMap.put("msg","邮箱格式不正确，请重新填写");
            return resultMap;
        }
        UserEntity temp = new UserEntity();
        temp.setEmail(email);
        int count = userDao.count(temp);
        if (count > 0){
            resultMap = ResponseEnum.PARAM_ERROR.getResultMap();
            resultMap.put("msg", "邮箱已被占用，请换一个试试");
            return resultMap;
        }
        if (StringUtils.isBlank(userEntity.getPassword()) || "******".equals(userEntity.getPassword())){
            resultMap = ResponseEnum.PARAM_ERROR.getResultMap();
            resultMap.put("msg", "密码不能为空或是******，请重新填写");
            return resultMap;
        }
        //加密存储密码
        String md5Str = MD5Util.MD5(md5Prefix + userEntity.getPassword());
        userEntity.setPassword(md5Str);
        int flag = userDao.add(userEntity);
        if (flag > 0){
            resultMap = ResponseEnum.SUCCESS.getResultMap();
        }else {
            resultMap = ResponseEnum.JDBC_ERROR.getResultMap();
        }
        return resultMap;
    }

    /**
     * 更新用户
     */
    public Map<String, Object> updateUser(UserEntity userEntity){
        Map<String, Object> resultMap;
        String email = userEntity.getEmail();
        if (StringUtils.isBlank(email) || !PatternUtil.isEmail(email)){
            resultMap = ResponseEnum.PARAM_ERROR.getResultMap();
            resultMap.put("msg","邮箱格式不正确，请重新填写");
            return resultMap;
        }
        /**
         * 根据Id查找用户，找到后判断是否修改了邮箱，保证不与其他邮箱重复
         */
        UserEntity temp = new UserEntity();
        temp.setId(userEntity.getId());
        List<UserEntity>  result = userDao.query(temp);
        if (ListUtil.isEmpty(result)){
            resultMap = ResponseEnum.PARAM_ERROR.getResultMap();
            resultMap.put("msg", "未找到该用户");
            return resultMap;
        }
        /**
         * 判断是否修改了该用户的邮箱
         * 如果邮箱被修改，判断是否其他用户已用该邮箱
         */
        if (!email.equals(result.get(0).getEmail())){
            //修改了邮箱，判断是否已被占用
            UserEntity user = new UserEntity();
            user.setEmail(email);
            int count = userDao.count(user);
            if (count > 0){
                resultMap = ResponseEnum.PARAM_ERROR.getResultMap();
                resultMap.put("msg", "邮箱已被占用，请换一个试试");
                return resultMap;
            }
        }
        /**
         * 如果密码非空且不是"******"，证明修改了用户密码，进行加密处理
         */
        if (StringUtils.isNotBlank(userEntity.getPassword())){
            if ("******".equals(userEntity.getPassword())){
                userEntity.setPassword("");
            }else {
                //加密存储密码
                String md5Str = MD5Util.MD5(md5Prefix + userEntity.getPassword());
                userEntity.setPassword(md5Str);
            }
        }
        userDao.update(userEntity);
        resultMap = ResponseEnum.SUCCESS.getResultMap();
        return resultMap;
    }

    /**
     * 删除用户
     */
    public void deleteUser(UserEntity userEntity){
        userDao.del(userEntity);
    }

    /**
     * 修改密码
     */
    public void updatePassword(UserEntity userEntity){
        String md5Str = MD5Util.MD5(md5Prefix + userEntity.getPassword());
        userEntity.setPassword(md5Str);
        userDao.updatePassword(userEntity);
    }

    /**
     * 批量删除
     */
    public Map<String, Object> deleteList(int[] id, int userId) {
        //
        userDao.delList(id, userId);

        return ResponseEnum.SUCCESS.getResultMap();
    }

}