package Controller.Main;

import Controller.Base.AbstractAccountCtrl;
import DataBase.Child.*;
import Obj.Main.*;

public class CustomerCtrl extends AbstractAccountCtrl
{
    //========================================Constructor=========================================
    public CustomerCtrl() { super(); }
    public CustomerCtrl(String id) { super(id); } 

    //==========================================Override==========================================
    @Override
    protected Customer queryInfo()
    {
        return new CustomerDb().queryCustomerData(id);
    }
    @Override
    protected <T> void updateInfo(T info)
    {
        new CustomerDb().updateCustomerData((Customer)info);
    }
}
