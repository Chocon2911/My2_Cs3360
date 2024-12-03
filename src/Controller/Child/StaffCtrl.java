package Controller.Child;

import Controller.Base.AbstractObjCtrl;
import DataBase.Child.*;
import Obj.Data.*;

public class StaffCtrl extends AbstractObjCtrl
{
    //========================================Constructor=========================================
    public StaffCtrl() { super(); }
    public StaffCtrl(String id) { super(id); }

    //==========================================Override==========================================
    @Override
    protected <T> String insertInfo(T info)
    {
        return new StaffDb().insertStaffData((Staff)info);
    }
    @Override
    @SuppressWarnings("unchecked")
    protected Staff queryInfo()
    {
        return new StaffDb().queryStaffData(id);
    }
    @Override 
    protected <T> String updateInfo(T info)
    {
        return new StaffDb().updateStaffData((Staff)info);
    }
}
