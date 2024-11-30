package DataBase.Base;

import java.sql.Blob;

public class DbData 
{
    //==========================================Variable==========================================
    private String valueStr;
    private int valueInt;
    private float valueFloat;
    private Blob valueBlob;
    private DbType dataBaseType;

    //========================================Constructor=========================================
    public DbData() {}
    public DbData(String valueStr) { this.setValueStr(valueStr); }
    public DbData(int valueInt) { this.setValueInt(valueInt); }
    public DbData(float valueFloat) { this.setValueFloat(valueFloat); }
    public DbData(Blob valueBlob) { this.setValueBlob(valueBlob); }

    //============================================Get=============================================
    public final String getValueStr() { return this.valueStr; }
    public final int getValueInt() { return this.valueInt; }
    public final float getValueFloat() { return this.valueFloat; }
    public final Blob getValueBlob() { return this.valueBlob; }
    public final DbType getDataBaseType() { return this.dataBaseType; }

    //===========================================Modify===========================================
    public final void setValueStr(String valueStr) 
    { 
        this.valueStr = valueStr;
        this.dataBaseType = DbType.TEXT;
    }
    public final void setValueInt(int valueInt) 
    {
        this.valueInt = valueInt;
        this.dataBaseType = DbType.INTEGER;
    }
    public final void setValueFloat(float valueFloat) 
    {
        this.valueFloat = valueFloat; 
        this.dataBaseType = DbType.FLOAT;
    }
    public final void setValueBlob(Blob valueBlob) 
    {
        this.valueBlob = valueBlob;
        this.dataBaseType = DbType.BLOB;
    }
}
