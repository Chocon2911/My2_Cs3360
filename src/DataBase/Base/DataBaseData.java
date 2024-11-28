package DataBase.Base;

import java.sql.Blob;

public class DataBaseData 
{
    //==========================================Variable==========================================
    private String valueStr;
    private int valueInt;
    private float valueFloat;
    private Blob valueBlob;
    private DataBaseType dataBaseType;

    //========================================Constructor=========================================
    public DataBaseData() {}
    public DataBaseData(String valueStr) { this.valueStr = valueStr; }
    public DataBaseData(int valueInt) { this.valueInt = valueInt; }
    public DataBaseData(float valueFloat) { this.valueFloat = valueFloat; }
    public DataBaseData(Blob valueBlob) { this.valueBlob = valueBlob; }

    //============================================Get=============================================
    public String getValueStr() { return this.valueStr; }
    public int getValueInt() { return this.valueInt; }
    public float getValueFloat() { return this.valueFloat; }
    public Blob getValueBlob() { return this.valueBlob; }
    public DataBaseType getDataBaseType() { return this.dataBaseType; }

    //===========================================Modify===========================================
    public void setValueStr(String valueStr) 
    { 
        if (this.dataBaseType == null)
        {
            this.displayNotNull();
            return;
        }
        this.valueStr = valueStr;
        this.dataBaseType = DataBaseType.TEXT;
    }
    public void setValueInt(int valueInt) 
    {
        if (this.dataBaseType == null)
        {
            this.displayNotNull();
            return;
        }
        this.valueInt = valueInt;
        this.dataBaseType = DataBaseType.INTEGER;
    }
    public void setValueFloat(float valueFloat) 
    { 
        if (this.dataBaseType == null)
        {
            this.displayNotNull();
            return;
        }
        this.valueFloat = valueFloat; 
        this.dataBaseType = DataBaseType.FLOAT;
    }
    public void setValueBlob(Blob valueBlob) 
    { 
        if (this.dataBaseType == null)
        {
            this.displayNotNull();
            return;
        }
        this.valueBlob = valueBlob;
        this.dataBaseType = DataBaseType.BLOB;
    }

    //===========================================Other============================================
    private void displayNotNull()
    {
        System.out.println("Value of this Obj is already set");
    }
}
