package com.ccb.realm;

import java.sql.Connection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.ccb.dao.UserDao;
import com.ccb.entity.User;
import com.ccb.util.DBUtil;

public class MyRealm extends AuthorizingRealm{

    private UserDao userDao=new UserDao();
    private DBUtil dbUtil=new DBUtil();

    /**
     * 为当前登录用户授予角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName=(String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        Connection con=null;
        try{
            con=dbUtil.getCon();
            authorizationInfo.setRoles(userDao.getRoles(con,userName));
            authorizationInfo.setStringPermissions(userDao.getPermissions(con,userName));
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return authorizationInfo;
    }

    /**
     *  验证当前登录用户
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName=(String)token.getPrincipal();
        Connection con=null;
        try{
            con=dbUtil.getCon();
            User user=userDao.getByUserName(con, userName);
            if(user!=null){
                AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),"xx");
                return authcInfo;
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

}