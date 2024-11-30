package Obj.Controller;

import DataBase.Child.*;
import Obj.Base.AbstractObjCtrl;
import Obj.Data.*;

public class CustomerCtrl extends AbstractObjCtrl
{
    //========================================Constructor=========================================
    public CustomerCtrl() { super(); }
    public CustomerCtrl(String id) { super(id); } 

    //==========================================Override==========================================
    @Override
    protected <T> String insertInfo(T info) 
    { 
        return new CustomerDb().insertCustomerData((Customer)info); 
    }
    @Override
    @SuppressWarnings("unchecked")
    protected Customer queryInfo()
    {
        return new CustomerDb().queryCustomerData(id);
    }
    @Override
    protected <T> String updateInfo(T info)
    {
        return new CustomerDb().updateCustomerData((Customer)info);
    }
}
