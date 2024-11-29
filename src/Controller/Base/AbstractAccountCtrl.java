package Controller.Base;

import Obj.Base.BaseAccount;

public abstract class AbstractAccountCtrl extends AbstractMainObjCtrl
{
    //========================================Constructor=========================================
    public AbstractAccountCtrl() { super(); }
    public AbstractAccountCtrl(String id) { super(id); }

    //============================================Get=============================================
    protected String getUserName() { return this.queryInfo().getUserName(); }
    protected String getPassword() { return this.queryInfo().getPassword(); }

    //==========================================Override==========================================
    @Override
    protected abstract BaseAccount queryInfo();
}
