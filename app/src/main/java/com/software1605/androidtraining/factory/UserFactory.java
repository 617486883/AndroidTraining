package com.software1605.androidtraining.factory;

import com.software1605.androidtraining.dao.UserDao;
import com.software1605.androidtraining.daoImpl.UserDaoInterentImpl;

public class UserFactory {
    public static UserDao getUserInterf(){

        return new UserDaoInterentImpl();
    }
}
