
package com.fengwei.paotui.Protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "orderlistreceivedResponse")
public class orderlistreceivedResponse  extends DataBaseModel
{

     @Column(name = "total")
     public int total;

     @Column(name = "more")
     public int more;

     @Column(name = "succeed")
     public int succeed;

     @Column(name = "count")
     public int count;

     @Column(name = "error_code")
     public int error_code;

     public ArrayList<ORDER_INFO>   orders = new ArrayList<ORDER_INFO>();

     @Column(name = "error_desc")
     public String   error_desc;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.total = jsonObject.optInt("total");

          this.more = jsonObject.optInt("more");

          this.succeed = jsonObject.optInt("succeed");

          this.count = jsonObject.optInt("count");

          this.error_code = jsonObject.optInt("error_code");

          subItemArray = jsonObject.optJSONArray("orders");
          if(null != subItemArray)
           {
              for(int i = 0;i < subItemArray.length();i++)
               {
                  JSONObject subItemObject = subItemArray.getJSONObject(i);
                  ORDER_INFO subItem = new ORDER_INFO();
                  subItem.fromJson(subItemObject);
                  this.orders.add(subItem);
               }
           }


          this.error_desc = jsonObject.optString("error_desc");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("total", total);
          localItemObject.put("more", more);
          localItemObject.put("succeed", succeed);
          localItemObject.put("count", count);
          localItemObject.put("error_code", error_code);

          for(int i =0; i< orders.size(); i++)
          {
              ORDER_INFO itemData =orders.get(i);
              JSONObject itemJSONObject = itemData.toJson();
              itemJSONArray.put(itemJSONObject);
          }
          localItemObject.put("orders", itemJSONArray);
          localItemObject.put("error_desc", error_desc);
          return localItemObject;
     }

}
