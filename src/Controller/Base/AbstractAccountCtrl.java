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

    //============================================Set=============================================
    private void setUserName(String userName) 
    { 
        BaseAccount info = this.queryInfo();
        info.setUserName(userName); 
        this.updateInfo(info);
    }
    private void setPassword(String password) 
    { 
        BaseAccount info = this.queryInfo();
        info.setPassword(password);
        this.updateInfo(info);
    }

    //==========================================Override==========================================
    @Override
    protected abstract BaseAccount queryInfo();
}
