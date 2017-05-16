package edu.xnxy.caojx.filemanager.service;

import edu.xnxy.caojx.filemanager.dao.IUserInfoDAO;
import edu.xnxy.caojx.filemanager.entity.UserInfo;
import edu.xnxy.caojx.filemanager.mybatis.mapper.pagination.PageParameter;
import edu.xnxy.caojx.filemanager.util.MD5;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Description: 用户信息维护u业务实现类
 *
 * @author caojx
 *         Created by caojx on 2017年04月11 下午10:04:04
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {

    private static final Logger log = Logger.getLogger(UserInfoServiceImpl.class);

    @Resource
    private IUserInfoDAO userInfoDAO;

    /**
     * Description: 用户登录，通过用户的userId去数据库中查询用户信息。
     * 如果查询到用户信息，比较密码是否正确，密码正确返回用户对象，密码不正确异常提示用户密码错误。
     * 如果没有查询到用户信息返回null。
     *
     * @param userId       用户编号
     * @param userPassword 用户密码
     * @return
     * @throws Exception
     */
    public UserInfo login(Long userId, String userPassword) throws Exception {
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            //对密码进行md5解密
            userPassword = MD5.getMD5Str(userPassword);
            //更具用户的userId查询用户，并判断用户是否存在
            UserInfo userInfo1 = userInfoDAO.get(userInfo);
            if (userInfo1 != null) {
                if (userPassword != null && userPassword.equals(userInfo1.getUserPassword())) {
                    return userInfo1;
                } else {
                    log.error(userId + "用户密码错误");
                    throw new Exception("用户密码错误");
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("用户信息查询失败", e);
            throw new Exception("用户信息查询失败", e);
        }
    }

    /**
     * Description: 查询用户
     *
     * @param userInfo
     * @return
     * @throws Exception
     */
    public UserInfo getUserInfo(UserInfo userInfo) throws Exception {
        UserInfo userInfo1 = null;
        try {
            userInfo1 = userInfoDAO.get(userInfo);
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            throw new RuntimeException("获取用户信息失败", e);
        }
        return userInfo1;
    }

    /**
     * Description: 查询用户信息
     *
     * @param userInfo
     * @return
     * @throws Exception
     */
    @Override
    public List<UserInfo> listUserInfo(UserInfo userInfo) throws Exception {
        List<UserInfo> userInfoList = null;
        try {
            userInfoList = userInfoDAO.query(userInfo);
        } catch (Exception e) {
            log.error("查询用户信息失败", e);
            throw new RuntimeException("查询用户信息失败", e);
        }
        return userInfoList;
    }

    /**
     * Description:查询用户信息,带分页
     *
     * @param userInfo
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public List<UserInfo> listUserInfo(UserInfo userInfo, PageParameter page) throws Exception {
        List<UserInfo> userInfoList = null;
        try {
            userInfoList = userInfoDAO.query(userInfo, page);
        } catch (Exception e) {
            log.error("查询用户信息失败", e);
            throw new RuntimeException("查询用户信息失败", e);
        }
        return userInfoList;
    }


    /**
     * Description:新增用户
     *
     * @param userInfo
     * @return
     * @throws Exception
     */
    public void saveUserInfo(UserInfo userInfo) throws Exception {
        UserInfo userInfo1 = null;
        try {
            userInfo1 = userInfoDAO.get(userInfo);
            if (userInfo1 == null) {
                userInfo.setCreateDate(new Date());
                userInfo.setRecStatus(1);
                //对密码进行md5加密
                userInfo.setUserPassword(MD5.getMD5Str(userInfo.getUserPassword()));
                userInfoDAO.insert(userInfo);
            } else {
                throw new RuntimeException("该用户已注册");
            }
        } catch (Exception e) {
            log.error("新增用户失败", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Description:更新用户信息
     *
     * @param userInfo
     * @throws Exception
     */
    public void updateUserInfo(UserInfo userInfo) throws Exception {
        try {
            userInfoDAO.update(userInfo);
        } catch (Exception e) {
            log.error("更新用户信息失败", e);
            throw new RuntimeException("更新用户信息失败", e);
        }
    }

    /**
     * Description:删除用户信息，注意并不是真的删除，只是将rec_status=0
     *
     * @param userInfo
     * @throws Exception
     */
    @Override
    public void removeUserInfo(UserInfo userInfo) throws Exception {
        try {
            userInfo.setRecStatus(0);
            userInfoDAO.update(userInfo);
        } catch (Exception e) {
            log.error("更新用户信息失败", e);
            throw new RuntimeException("更新用户信息失败", e);
        }
    }

}
