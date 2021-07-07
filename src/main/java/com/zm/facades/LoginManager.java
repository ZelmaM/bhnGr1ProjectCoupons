/*
Creating by Zelma Milev
*/
package com.zm.facades;

import com.zm.beans.ClientType;

import java.sql.SQLException;

public class LoginManager {
    //1
    private static LoginManager instance = null;

    //2
    private LoginManager() {
        System.out.println("CTOR IN ACTION - " + this.getClass().getSimpleName());
    }

    //3


    public /*synchronized*/ static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws SQLException {
        ClientFacade clientFacade = null;

        switch (clientType) {
            case COMPANY:
                clientFacade = new CompanyFacade();
                if (clientFacade.login(email, password)) {
                    ((CompanyFacade) clientFacade).setCompanyID(( clientFacade).companyDao.getCompanyID(email, password));
                    System.out.println("COMPANY #"+((CompanyFacade) clientFacade).getCompanyID()+" access permitted ");
                } else return null;
                break;
            case CUSTOMER:
                clientFacade = new CustomerFacade();
                if (clientFacade.login(email, password)) {
                    ((CustomerFacade) clientFacade).setCustomerID(clientFacade.customerDao.getCustomerID(email, password));
                    System.out.println("CUSTOMER #"+((CustomerFacade) clientFacade).getCustomerID()+" access permitted ");
                } else return null;
                break;
            case ADMINISTRATOR:
                clientFacade = new AdminFacade();
                if (!clientFacade.login(email, password)) {
                    return null;
                } else System.out.println("ADMINISTRATOR access permitted ");
                break;
            default:
                break;
        }
        return clientFacade;

    }
}