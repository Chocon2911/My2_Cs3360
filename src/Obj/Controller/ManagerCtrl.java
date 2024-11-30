package Obj.Controller;

import DataBase.Child.ManagerDb;
import Obj.Base.AbstractObjCtrl;
import Obj.Data.*;

public class ManagerCtrl extends AbstractObjCtrl
{
    //========================================Constructor=========================================
    public ManagerCtrl() { super(); }
    public ManagerCtrl(String id) { super(id); }

    //==========================================Override==========================================  
    @Override
    protected <T> String insertInfo(T info)
    {
        return new ManagerDb().insertManagerData((Manager)info);
    }
    @Override
    @SuppressWarnings("unchecked")
    protected Manager queryInfo() 
    { 
        return new ManagerDb().queryManagerData(id); 
    }
    @Override
    protected <T> String updateInfo(T info)
    {
        return new ManagerDb().updateManagerData((Manager)info);
    }
}
