package Obj.Base;

public abstract class AbstractAccount extends AbstractMainObj
{
	//==========================================Variable==========================================
	protected String userName;
	protected String password;
	protected boolean isLogin;

	//========================================Constructor=========================================
	public AbstractAccount()
	{
        super();
		this.userName = null;
		this.password = null;
		this.isLogin = false;
	}

	public AbstractAccount(String id, String name, String userName, String password, boolean isLogin)
	{
        super(id, name);
		this.userName = userName;
		this.password = password;
		this.isLogin = isLogin;
	}

	//============================================Get=============================================
	public String getUserName(){ return this.userName; }
	public String getPassword() { return this.password; }
	public boolean getIsLogin() { return this.isLogin; }

	//============================================Set=============================================
	public void setUserName(String userName) { this.userName = userName; }
	public void setPassword(String password) { this.password = password; }
	public void setIsLogin(boolean isLogin) { this.isLogin = isLogin; }
}
