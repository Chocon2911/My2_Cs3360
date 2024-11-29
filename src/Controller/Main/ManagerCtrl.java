package Controller.Main;

import Controller.Base.AbstractAccountCtrl;
import DataBase.Child.ManagerDb;
import Obj.Main.*;

public class ManagerCtrl extends AbstractAccountCtrl
{
    //========================================Constructor=========================================
    public ManagerCtrl() { super(); }
    public ManagerCtrl(String id) { super(id); }

    //==========================================Override==========================================  
    @Override
    protected Manager queryInfo() 
    { 
        return new ManagerDb().queryManagerData(id); 
    }
    @Override
    protected <T> void updateInfo(T info)
    {
        new ManagerDb().updateManagerData((Manager)info);
    }
}
