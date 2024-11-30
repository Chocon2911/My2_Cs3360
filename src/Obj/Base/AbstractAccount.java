package Obj.Base;

public abstract class AbstractAccount extends AbstractMainObj
{
	//==========================================Variable==========================================
	protected String userName;
	protected String password;

	//========================================Constructor=========================================
	public AbstractAccount()
	{
        super();
		this.userName = null;
		this.password = null;
	}

	public AbstractAccount(String id, String name, String userName, String password)
	{
        super(id, name);
		this.userName = userName;
		this.password = password;
	}

	//============================================Get=============================================
	public String getUserName(){ return this.userName; }
	public String getPassword() { return this.password; }

	//============================================Set=============================================
	public void setUserName(String userName) { this.userName = userName; }
	public void setPassword(String password) { this.password = password; }
}
