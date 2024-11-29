package Controller.Main;

import Controller.Base.AbstractMainObjCtrl;
import DataBase.Child.CustomerRequestDb;
import Obj.Main.*;

public class CustomerRequestCtrl extends AbstractMainObjCtrl
{
    //========================================Constructor=========================================
    public CustomerRequestCtrl(String id) { super(id); }
    public CustomerRequestCtrl() { super(); }

    //==========================================Override==========================================
    @Override
    protected CustomerRequest queryInfo()
    {
        return new CustomerRequestDb().queryCustomerRequestData(id);
    }
    @Override 
    protected <T> void updateInfo(T info)
    {
        new CustomerRequestDb().updateCustomerRequestData((CustomerRequest)info);
    }
}
