package com.grgbanking.framework.manager.mapper;

import com.grgbanking.framework.domains.manager.user.param.UserListParam;
import com.grgbanking.framework.domains.manager.user.param.UserLoginParam;
import com.grgbanking.framework.domains.manager.user.pojo.UserPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wyf on 2017/8/1.
 */
@Service("UserMapper")
public interface UserMapper extends BaseMapper {

    /**
     * 根据用户名和密码查找用户
     * @param userLoginParam
     * @return
     */
    UserPojo getUserByUsername(UserLoginParam userLoginParam);

    UserPojo getAuthorityByUsername(UserPojo userPojo);

    List<UserPojo> getUserList(UserListParam userListParam);

    List<UserPojo> getUserListByAdmin(UserListParam userListParam);

    UserPojo getUserInfo(UserPojo userPojo);

    UserPojo getUserDetial(UserPojo userPojo);

    Integer checkIsExistUser(UserPojo userPojo);

    void addUser(UserPojo userPojo);

    void editUser(UserPojo userPojo);

    void resertPassword(@Param("id") Long userId);

    void updatePassword(@Param("id") Long userId,@Param("password") String password);

    void clearUserRole(@Param("user_id") Long userId);

    void bindUserRole(@Param("user_id") Long userId,@Param("role_id") Long roleId);

    void deleteUser(UserPojo userPojo);

    Long getUserCount(UserListParam userListParam);

    Long getUserCountByAdmin(UserListParam userListParam);


}
