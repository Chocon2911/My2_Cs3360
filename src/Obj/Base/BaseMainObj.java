package Obj.Base;

public class BaseMainObj extends BaseObj
{
	//==========================================Variable==========================================
	protected String name;

	//========================================Constructor=========================================
	public BaseMainObj()
	{		
        this.name = null;
	}

	public BaseMainObj(String id, String name)
	{
        super(id);
		this.name = name;
	}

	//============================================Get=============================================
	public String getName() { return this.name; }

	//============================================Set=============================================
	public void setName(String name) { this.name = name; }

}
