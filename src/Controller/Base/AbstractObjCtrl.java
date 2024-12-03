package Controller.Base;

import Obj.Base.AbstractObj;

public abstract class AbstractObjCtrl extends AbstractObj
{
    //========================================Constructor=========================================
    public AbstractObjCtrl() {}
    public AbstractObjCtrl(String id) { super(id); }

    //==========================================Abstract==========================================
    protected abstract <T> String insertInfo(T info);
    protected abstract <T> T queryInfo();
    protected abstract <T> String updateInfo(T info);
}
