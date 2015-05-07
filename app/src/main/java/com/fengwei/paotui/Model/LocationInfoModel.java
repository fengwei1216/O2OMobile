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

package com.fengwei.paotui.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.fengwei.paotui.PaotuiAppConst;
import com.fengwei.paotui.Protocol.ApiInterface;
import com.fengwei.paotui.Protocol.locationinfoRequest;
import com.fengwei.paotui.Protocol.locationinfoResponse;
import com.fengwei.paotui.Utils.LocationManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LocationInfoModel extends BaseModel {

	public String publicLocationName = "";
	public LocationInfoModel(Context context) {
		super(context);
	}
	
	public void get() {
		locationinfoRequest request = new locationinfoRequest();
		request.ver = PaotuiAppConst.VERSION_CODE;
		request.lat = LocationManager.getLatitude();
		request.lon = LocationManager.getLongitude();
		
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					LocationInfoModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        locationinfoResponse response = new locationinfoResponse();
                        response.fromJson(jo);
                        if(response.succeed == 1) {
                        	if(response.location != null && response.location.name != null) {
                                publicLocationName = response.location.name;
                        	}
                        	LocationInfoModel.this.OnMessageResponse(url,jo,status);
                        } else {
                        	LocationInfoModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }
				} catch (JSONException e) {

				}
			}
		};

		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("json", request.toJson().toString());
		} catch (JSONException e) { 
			
		}
		cb.url(ApiInterface.LOCATION_INFO).type(JSONObject.class).params(params);
        ajax(cb);
	}
	
}
