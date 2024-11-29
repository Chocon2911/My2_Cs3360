package Controller.Main;

import Controller.Base.AbstractObjCtrl;
import DataBase.Child.*;
import Obj.Main.*;

public class RequestedItemCtrl extends AbstractObjCtrl
{
    //========================================Constructor=========================================
    public RequestedItemCtrl() { super(); }
    public RequestedItemCtrl(String id) { super(id); }

    //==========================================Override==========================================
    @Override
    @SuppressWarnings("unchecked")
    protected RequestedItem queryInfo()
    {
        return new RequestedItemDb().queryRequestedItemData(id);
    }
    @Override
    protected <T> void updateInfo(T info) 
    {
        new RequestedItemDb().updateRequestedItemData((RequestedItem)info);    
    }
}
