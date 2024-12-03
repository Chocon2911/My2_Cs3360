package Controller.Child;

import Controller.Base.AbstractObjCtrl;
import DataBase.Child.*;
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
        return CustomerDb.getInstance().insertCustomerData((Customer)info); 
    }
    @Override
    @SuppressWarnings("unchecked")
    protected Customer queryInfo()
    {
        return CustomerDb.getInstance().queryCustomerData(id);
    }
    @Override
    protected <T> String updateInfo(T info)
    {
        return CustomerDb.getInstance().updateCustomerData((Customer)info);
    }
}
