//
//       _/_/_/                      _/            _/_/_/_/_/
//    _/          _/_/      _/_/    _/  _/              _/      _/_/      _/_/
//   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/              _/      _/    _/  _/    _/
//  _/    _/  _/        _/        _/  _/          _/        _/    _/  _/    _/
//   _/_/_/    _/_/_/    _/_/_/  _/    _/      _/_/_/_/_/    _/_/      _/_/
//
//
//  Copyright (c) 2015-2016, feng wei Studio
//  http://www.geek-zoo.com
//
//
//  Permission is hereby granted, free of charge, to any person obtaining a
//  copy of this software and associated documentation files (the "Software"),
//  to deal in the Software without restriction, including without limitation
//  the rights to use, copy, modify, merge, publish, distribute, sublicense,
//  and/or sell copies of the Software, and to permit persons to whom the
//  Software is furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
//  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
//  IN THE SOFTWARE.
//

package com.fengwei.paotui.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "ORDER_RECORD")
public class ORDER_RECORD  extends DataBaseModel
{

     @Column(name = "ORDER_RECORD_id")
     public int id;

     @Column(name = "order_action")
     public int order_action;

     @Column(name = "created_at")
     public String   created_at;

     @Column(name = "active")
     public int active;

     @Column(name = "user")
     public SIMPLE_USER   user;

     @Column(name = "note")
     public String   note;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.id = jsonObject.optInt("id");

          this.order_action = jsonObject.optInt("order_action");

          this.created_at = jsonObject.optString("created_at");

          this.active = jsonObject.optInt("active");
          SIMPLE_USER  user = new SIMPLE_USER();
          user.fromJson(jsonObject.optJSONObject("user"));
          this.user = user;

          this.note = jsonObject.optString("note");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("id", id);
          localItemObject.put("order_action", order_action);
          localItemObject.put("created_at", created_at);
          localItemObject.put("active", active);
          if(null != user)
          {
            localItemObject.put("user", user.toJson());
          }
          localItemObject.put("note", note);
          return localItemObject;
     }

}
