package com.isoftston.issuser.fusioncharge.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;


import com.corelibs.common.AppManager;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.rxbus.RxBus;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.wechatpay.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;




public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	/**
	 * 0是下单成功立即付款 1是在订单中付款
	 */
	public static String time;

	private IWXAPI api;

	private Context context = WXPayEntryActivity.this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_wechatpay_result);

		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		// -1 签名之类不对 -2 用户取消 0成功

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {



			if (resp.errCode == -1) {
				// 进入支付失败页面
				ToastMgr.show("支付失败");

				finish();
			} else if (resp.errCode == -2) {
				ToastMgr.show("支付取消");

				finish();

			} else if (resp.errCode == 0) {
				// 支付成功
				ToastMgr.show("支付成功，请等待审核");
//				if (AppManager.getAppManager().getActivity(PayActivity.class)!=null){
//					AppManager.getAppManager().finishActivity(PayActivity.class);
//				}
//				while (true) {
////					if (AppManager.getAppManager().currentActivity() instanceof MyMemberActivity) {
//////							                        ((MainActivity)(AppManager.getAppManager().currentActivity() ));
////						break;
////					} else {
////						AppManager.getAppManager().finishActivity();
////					}
//					finish();
//				}
//				RxBus.getDefault().send(new PaySuccessBean(), Constant.PAY_SUCCESS);
				finish();

			}





		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

}