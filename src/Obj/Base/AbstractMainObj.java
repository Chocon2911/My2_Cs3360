package Obj.Base;

public abstract class AbstractMainObj extends AbstractObj
{
	//==========================================Variable==========================================
	protected String name;

	//========================================Constructor=========================================
	public AbstractMainObj()
	{		
        this.name = null;
	}

	public AbstractMainObj(String id, String name)
	{
        super(id);
		this.name = name;
	}

	//============================================Get=============================================
	public String getName() { return this.name; }

	//============================================Set=============================================
	public void setName(String name) { this.name = name; }

}
