package com.woodwang.dao;

import org.sketch.orm.base.GenericDao;
import org.springframework.stereotype.Repository;

import com.woodwang.bean.User;

/**
 * DAO类提供一个实体类型，且继承于SequoiaDB ORM框架的基础类GenericDao类
 * @author admin
 *
 */
@Repository
public class UserDao extends GenericDao<User>{

}
