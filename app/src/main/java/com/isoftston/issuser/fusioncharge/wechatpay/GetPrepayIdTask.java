package com.isoftston.issuser.fusioncharge.wechatpay;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;


import com.corelibs.common.AppManager;
import com.corelibs.views.LoadingDialog;
import com.isoftston.issuser.fusioncharge.constants.Urls;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


/**
 * 微信支付
 * 
 * @author wangl01
 *
 */
public class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {

	private StringBuffer sb = new StringBuffer();
	private Map<String, String> resultunifiedorder;
	private PayReq req = new PayReq();
	private IWXAPI msgApi;
	private Context c;
	private double price;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 多个订单
	 */
	private String orderNos;

	private String prepay_id;

	private LoadingDialog loadingDialog;

	/**
	 * 微信支付 0 扫码支付1
	 */
	private int type;

	private String code_url;


	public GetPrepayIdTask(Context c, double price, String orderNo, String orderNos,int type) {
		this.c = c;
		msgApi = WXAPIFactory.createWXAPI(c, null);
		msgApi.registerApp(Constants.APP_ID);
		this.price = price;
		this.orderNo = orderNo;
		this.type=type;
		this.orderNos = orderNos;
		this.loadingDialog = new LoadingDialog(c);
	}

	private LoadingDialog dialog;

	@Override
	protected void onPreExecute() {
		dialog = new LoadingDialog(c,"正在加载");
	}

	@Override
	protected void onPostExecute(Map<String, String> result) {
		if (dialog != null) {
			dialog.dismiss();
		}

		loadingDialog.show();

		Log.e("result",result.toString());

		if (type==0){
			prepay_id=result.get("prepay_id");

			sb.append("prepay_id\n" + prepay_id + "\n\n");
//
		genPayReq();
	//	msgApi.registerApp(Constants.APP_ID);
		loadingDialog.dismiss();
		msgApi.sendReq(req);
		}
//		else if (type==1){
//			code_url=result.get("code_url");
//			c.startActivity(QRCodeActivity.getLaunchIntent(c,code_url,0));
//			if (!(AppManager.getAppManager().getActivity(WalletPayActivity.class) == null)) {
//				AppManager.getAppManager().finishActivity(WalletPayActivity.class);
//			}
//		}

	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	@Override
	protected Map<String, String> doInBackground(Void... params) {

		String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
		String entity = genProductArgs();

		byte[] buf = Util.httpPost(url, entity);

		String content = new String(buf);
		Log.e("orion", content);
		Map<String, String> xml = decodeXml(content);

		return xml;
	}

	private void genPayReq() {

		req.appId = Constants.APP_ID;
		req.partnerId = Constants.MCH_ID;
		Log.e("预支付id",prepay_id+"");
		req.prepayId = prepay_id;
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId ));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		sb.append("sign\n" + req.sign + "\n\n");

		Log.e("orion", signParams.toString());

	}

	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		this.sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orionAppSign", appSign);
		return appSign;
	}

	private String genProductArgs() {
		StringBuffer xml = new StringBuffer();

		try {
			String nonceStr = genNonceStr();

			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
			packageParams.add(new BasicNameValuePair("body", orderNo + ""));
			packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url", Urls.ROOT+"payBack/wechatCallBack"));

			// 订单
			packageParams.add(new BasicNameValuePair("out_trade_no", (orderNos)));
			// 订单价格
			long total_fee = (long) (price*100);
			// 订单
			packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
			packageParams.add(new BasicNameValuePair("total_fee", total_fee + ""));
			if (type==0){
				packageParams.add(new BasicNameValuePair("trade_type", "APP"));
			}else {
				//packageParams.add(new BasicNameValuePair("product_id",new Random().nextInt(10000)+""));
				packageParams.add(new BasicNameValuePair("trade_type", "NATIVE"));
			}

			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

			String xmlstring = toXml(packageParams);

			return xmlstring;

		} catch (Exception e) {
			// Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
			return null;
		}

	}

	public Map<String, String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName = parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT:

						break;
					case XmlPullParser.START_TAG:

						if ("xml".equals(nodeName) == false) {
							// 实例化student对象
							xml.put(nodeName, parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion", e.toString());
		}
		return null;

	}

	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 生成签名
	 */

	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		Log.e("orionStringBuilder", sb.toString());
		Log.e("orionAppMd5", packageSign);
		return packageSign;
	}

	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");

			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");

		Log.e("orion", sb.toString());
		return sb.toString();
	}

	/**
	 * 订单号信息
	 *
	 * @return
	 */
	private String genOutTradNo(String orderNos) {
		return MD5.getMessageDigest(String.valueOf(orderNos).getBytes());
	}

}
