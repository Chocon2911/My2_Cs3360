package Obj.Base;

import Util.ObjUtil;

public abstract class AbstractObj extends ObjUtil
{
	//==========================================Variable==========================================
	protected String id;

	//========================================Constructor=========================================
	public AbstractObj()
	{		
        this.id = null;
	}

	public AbstractObj(String id)
	{
		this.id = id;
	}

	//============================================Get=============================================
	public String getId(){ return this.id; }

	//============================================Set=============================================
	public void setId(String id) { this.id = id; }
}