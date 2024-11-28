package Controller.Base;

import Obj.Base.BaseMainObj;

public abstract class AbstractMainObjCtrl extends AbstractObjCtrl 
{
    //========================================Constructor=========================================
    public AbstractMainObjCtrl() { super(); }
    public AbstractMainObjCtrl(String id) { super(id); }

    //============================================Get=============================================
    protected String getName() { return this.queryInfo().getName(); }

    //============================================Set=============================================
    private void setName(String name) 
    { 
        BaseMainObj info = this.queryInfo();
        info.setName(name); 
        this.updateInfo(info);
    }

    //==========================================Override==========================================
    @Override
    @SuppressWarnings("unchecked")
    protected abstract BaseMainObj queryInfo();
}
