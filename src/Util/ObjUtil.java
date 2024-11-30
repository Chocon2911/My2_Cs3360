package Util;

import java.util.Random;

import Obj.Data.ItemType;

public abstract class ObjUtil extends GuiUtil
{
    //===========================================Other============================================
    protected String getRandomStr(int length)
    {
        if (length <= 0)
        {
            System.out.println("ERROR: getRandomStr(): length is <= 0");
            return null;
        }

        Random rand = new Random();
        String randChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randStr = new StringBuilder();

        for (int i = 0; i < length; i++)
        {
            int index = rand.nextInt(randChar.length());
            randStr.append(randChar.charAt(index));
        }

        return randStr.toString();
    }

    //==========================================Convert===========================================
    protected ItemType getItemTypeFromInt(int value)
    {
        if (value == 1) return ItemType.Food;
        else if (value == 2) return ItemType.Cloth;
        else if (value == 3) return ItemType.Tool;
        else return null;
    }

    protected int getIntFromItemType(ItemType itemType)
    {
        if (itemType == ItemType.Food) return 1;
        else if (itemType == ItemType.Cloth) return 2;
        else if (itemType == ItemType.Tool) return 3;
        else return 0;
    }
}
