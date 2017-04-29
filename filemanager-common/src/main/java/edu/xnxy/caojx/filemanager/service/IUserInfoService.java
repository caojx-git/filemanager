package edu.xnxy.caojx.filemanager.service;

import edu.xnxy.caojx.filemanager.entity.UserInfo;
import edu.xnxy.caojx.filemanager.mybatis.mapper.pagination.PageParameter;

import java.util.List;
import java.util.Map;

/**
 * Description: 用户信息维护业务接口
 *
 * @author caojx
 *         Created by caojx on 2017年04月11 下午9:24:24
 */
public interface IUserInfoService {

    /**
     * Description: 用户登录
     *
     * @param userId
     * @param userPassword
     * @return
     * @throws Exception
     */
    public UserInfo login(Long userId, String userPassword) throws Exception;

    /**
     * Description:查询用户
     *
     * @param userInfo
     * @return
     * @throws Exception
     */
    public UserInfo getUserInfo(UserInfo userInfo) throws Exception;

    /**
     * Description:查询用户信息
     *
     * @param userInfo
     * @return
     * @throws Exception
     */
    public List<UserInfo> listUserInfo(UserInfo userInfo) throws Exception;

    /**
     * Description:查询用户信息,带分页
     *
     * @param userInfo
     * @return
     * @throws Exception
     */
    public List<UserInfo> listUserInfo(UserInfo userInfo, PageParameter page) throws Exception;

    /**
     * Description:新增用户
     *
     * @param userInfo
     * @return
     * @throws Exception
     */
    public void saveUserInfo(UserInfo userInfo) throws Exception;

    /**
     * Description:更新用户信息
     *
     * @param userInfo
     * @throws Exception
     */
    public void updateUserInfo(UserInfo userInfo) throws Exception;

    /**
     * Description:删除用户信息，注意并不是真的删除，只是将rec_status=0
     *
     * @param userInfo
     * @throws Exception
     */
    public void removeUserInfo(UserInfo userInfo) throws Exception;


}

