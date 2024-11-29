package Controller.Main;

import Controller.Base.AbstractAccountCtrl;
import DataBase.Child.*;
import Obj.Main.*;

public class StaffCtrl extends AbstractAccountCtrl
{
    //========================================Constructor=========================================
    public StaffCtrl() { super(); }
    public StaffCtrl(String id) { super(id); }

    //==========================================Override==========================================
    @Override
    protected Staff queryInfo()
    {
        return new StaffDb().queryStaffData(id);
    }
    @Override 
    protected <T> void updateInfo(T info)
    {
        new StaffDb().updateStaffData((Staff)info);
    }
}
