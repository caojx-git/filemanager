package edu.xnxy.caojx.filemanager.web;

import edu.xnxy.caojx.filemanager.entity.FileManagerSysBaseType;
import edu.xnxy.caojx.filemanager.entity.UserInfo;
import edu.xnxy.caojx.filemanager.mybatis.mapper.pagination.PageParameter;
import edu.xnxy.caojx.filemanager.service.IFileManagerSysBaseTypeService;
import edu.xnxy.caojx.filemanager.service.IUserInfoService;
import edu.xnxy.caojx.filemanager.web.util.MD5;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 用户管理Controller
 *
 * @author caojx
 *         Created by caojx on 2017年04月20 上午9:59:59
 */
@Controller
@RequestMapping("/filter/userManager")
public class UserManagerController {

    private static final Logger log = Logger.getLogger(UserManagerController.class);

    @Resource
    private IFileManagerSysBaseTypeService fileManagerSysBaseTypeService;

    @Resource
    private IUserInfoService userInfoService;

    @RequestMapping("/addUserPage.do")
    public String showAddUserPage() {
        return "addUser";
    }

    /**
     * Description：返回用户管理页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/userManagerPage.do")
    public ModelAndView showUserManagerPage(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            HttpSession session = request.getSession();
            UserInfo userInfo = new UserInfo();
            UserInfo userInfo1 = (UserInfo) session.getAttribute("userInfo");
            if (userInfo1.getManagerType() != 1) {
                userInfo.setCollegeId(userInfo1.getCollegeId());
            }
            PageParameter page = new PageParameter();
            List<UserInfo> userInfoList = userInfoService.listUserInfo(userInfo, page);
            resultMap.put("status", 0);
            resultMap.put("page", page);
            resultMap.put("userInfoList", userInfoList);
        } catch (Exception e) {
            log.error("用户信息加载失败", e);
            resultMap.put("status", 0);
            resultMap.put("message", "用户信息加载出错");
        }
        return new ModelAndView("userManager", resultMap);
    }

    /**
     * Description : 编辑用户信息页面
     *
     * @param userInfo
     * @param request
     * @return
     */
    @RequestMapping("/editUserInfoPage.do")
    public ModelAndView showEditUserInfoPage(UserInfo userInfo, HttpServletRequest request) {
        Map<String, Object> resultMap = null;
        try {
            resultMap = new HashMap<String, Object>();
            HttpSession session = request.getSession();
            userInfo = userInfoService.getUserInfo(userInfo);
            resultMap.put("status", 0);
            resultMap.put("userInfo", userInfo);
        } catch (Exception e) {
            resultMap.put("status", 1);
            resultMap.put("message", "获取用户信息失败");
            log.error("查询用户信息失败", e);
        }
        return new ModelAndView("editUser", resultMap);
    }

    /**
     * Description:查看用户信息界面
     *
     * @param userInfo
     * @param request
     * @return
     */
    @RequestMapping("/viewUserPage.do")
    public ModelAndView showViewUserPage(UserInfo userInfo, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            userInfo = userInfoService.getUserInfo(userInfo);
            resultMap.put("status", 0);
            resultMap.put("userInfo", userInfo);
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            resultMap.put("status", 1);
            resultMap.put("message", "获取用户信息失败");
        }
        return new ModelAndView("viewUser", resultMap);
    }


    /**
     * Description 获取用户信息列表
     *
     * @param userInfo
     * @param request
     * @param page
     * @return
     */
    @RequestMapping("/userInfoList.do")
    public ModelAndView listUserInfo(UserInfo userInfo, HttpServletRequest request, PageParameter page) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            HttpSession session = request.getSession();
            UserInfo userInfo1 = (UserInfo) session.getAttribute("userInfo");
            if (userInfo1.getManagerType() != 1) {
                userInfo.setCollegeId(userInfo1.getCollegeId());
            }
            List<UserInfo> userInfoList = userInfoService.listUserInfo(userInfo, page);
            resultMap.put("status", 0);
            resultMap.put("userInfoList", userInfoList);
            resultMap.put("userId", userInfo.getUserId());
            resultMap.put("userName", userInfo.getUserName());
            resultMap.put("collegeId", userInfo.getCollegeId());
            resultMap.put("manager", userInfo.getManager());
            resultMap.put("userInfoList", userInfoList);
            resultMap.put("page", page);
        } catch (Exception e) {
            log.error("查询失败", e);
            resultMap.put("status", 1);
            resultMap.put("message", "查询失败");

        }
        return new ModelAndView("userManager", resultMap);
    }

    /**
     * Description:新增用户
     *
     * @param userInfo
     * @param request
     * @return
     */
    @RequestMapping("/addUser.do")
    @ResponseBody
    public Map<String, Object> addUser(UserInfo userInfo, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            HttpSession session = request.getSession();
            UserInfo userInfo1 = (UserInfo) session.getAttribute("userInfo");
            if (userInfo1.getManagerType() != 1) {
                userInfo.setManager(0);
            }
            userInfoService.saveUserInfo(userInfo);
            resultMap.put("status", 0);
            resultMap.put("message", "添加成功");
        } catch (Exception e) {
            log.error("添加失败", e);
            resultMap.put("status", 1);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }


    /**
     * Description: 删除用户信息
     *
     * @param userInfo
     * @return
     */
    @RequestMapping("/removeUserInfo.do")
    @ResponseBody
    public Map<String, Object> removeUserInfo(UserInfo userInfo) {
        Map<String, Object> resultMap = null;
        try {
            resultMap = new HashMap<String, Object>();
            userInfoService.removeUserInfo(userInfo);
            resultMap.put("status", 0);
        } catch (Exception e) {
            resultMap.put("status", 1);
            resultMap.put("message", "删除用户信息失败");
            log.error("删除用户信息失败", e);
        }
        return resultMap;
    }

    /**
     * Description:更新用户信息
     *
     * @param userInfo
     * @return
     */
    @RequestMapping("/updateUserInfo.do")
    @ResponseBody
    public Map<String, Object> updateUserInfo(UserInfo userInfo) {
        Map<String, Object> resultMap = null;
        try {
            resultMap = new HashMap<String, Object>();
            userInfoService.updateUserInfo(userInfo);
            resultMap.put("status", 0);
        } catch (Exception e) {
            resultMap.put("status", 1);
            resultMap.put("message", "保存用户信息失败");
            log.error("保存用户信息失败", e);
        }
        return resultMap;
    }


}
