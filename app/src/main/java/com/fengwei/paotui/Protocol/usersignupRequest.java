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

@Table(name = "usersignupRequest")
public class usersignupRequest  extends DataBaseModel
{

     @Column(name = "platform")
     public String   platform;

     @Column(name = "mobile_phone")
     public String   mobile_phone;

     @Column(name = "location")
     public LOCATION   location;

     @Column(name = "nickname")
     public String   nickname;

     @Column(name = "invite_code")
     public String   invite_code;

     @Column(name = "ver")
     public int ver;

     @Column(name = "password")
     public String   password;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.platform = jsonObject.optString("platform");

          this.mobile_phone = jsonObject.optString("mobile_phone");
          LOCATION  location = new LOCATION();
          location.fromJson(jsonObject.optJSONObject("location"));
          this.location = location;

          this.nickname = jsonObject.optString("nickname");

          this.invite_code = jsonObject.optString("invite_code");

          this.ver = jsonObject.optInt("ver");

          this.password = jsonObject.optString("password");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("platform", platform);
          localItemObject.put("mobile_phone", mobile_phone);
          if(null != location)
          {
            localItemObject.put("location", location.toJson());
          }
          localItemObject.put("nickname", nickname);
          localItemObject.put("invite_code", invite_code);
          localItemObject.put("ver", ver);
          localItemObject.put("password", password);
          return localItemObject;
     }

}
