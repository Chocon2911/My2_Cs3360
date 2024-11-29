package Controller.Base;

import Obj.Base.BaseMainObj;

public abstract class AbstractMainObjCtrl extends AbstractObjCtrl 
{
    //========================================Constructor=========================================
    public AbstractMainObjCtrl() { super(); }
    public AbstractMainObjCtrl(String id) { super(id); }

    //==========================================Override==========================================
    @Override
    @SuppressWarnings("unchecked")
    protected abstract BaseMainObj queryInfo();
}
