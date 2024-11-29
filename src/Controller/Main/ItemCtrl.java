package Controller.Main;

import Controller.Base.AbstractMainObjCtrl;
import DataBase.Child.ItemDb;
import Obj.Main.*;

public class ItemCtrl extends AbstractMainObjCtrl
{
    //========================================Constructor=========================================
    public ItemCtrl() { super(); }
    public ItemCtrl(String id) { super(id); }

    //==========================================Override==========================================
    @Override
    protected Item queryInfo()
    {
        return new ItemDb().queryItemData(id);
    }
    @Override 
    protected <T> void updateInfo(T info)
    {
        new ItemDb().updateItemData((Item)info);
    }
}
