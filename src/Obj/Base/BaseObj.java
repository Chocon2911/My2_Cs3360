package Obj.Base;

import Util.ObjUtil;

public class BaseObj extends ObjUtil
{
	//==========================================Variable==========================================
	protected String id;

	//========================================Constructor=========================================
	public BaseObj()
	{		
        this.id = null;
	}

	public BaseObj(String id)
	{
		this.id = id;
	}

	//============================================Get=============================================
	public String getId(){ return this.id; }

	//============================================Set=============================================
	public void setId(String id) { this.id = id; }
}