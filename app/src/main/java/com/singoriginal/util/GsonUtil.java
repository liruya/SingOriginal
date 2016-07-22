package com.singoriginal.util;

import com.google.gson.Gson;
import com.singoriginal.constant.ConstVal;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * GSON解析类
 * Created by lanouhn on 16/7/20.
 */
public class GsonUtil
{
    private static final Gson gson = new Gson();
    /**
     * 从获取的字符串格式json中提取出所需的对象集合(字符串格式)
     * @param json
     * @return
     */
    public static String getJsonArray(String json)
    {
        if ( json.startsWith("{") && json.endsWith("}") )
        {
            try
            {
                JSONObject obj = new JSONObject(json);
                if (obj.has(ConstVal.SUCCESS))
                {
                    Boolean success = obj.getBoolean(ConstVal.SUCCESS);
                    if (success)
                    {
                        if (obj.has(ConstVal.DATA))
                        {
                            return obj.getString(ConstVal.DATA);
                        }
                        else
                        {
                            return null;
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    return null;
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        else if ( json.startsWith("[") && json.endsWith("]") )
        {
            return json;
        }
        return null;
    }
}