package com.isoftston.issuser.fusioncharge.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.common.AppManager;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.rxbus.RxBus;
import com.corelibs.views.NoScrollingListView;
import com.isoftston.issuser.fusioncharge.App;
import com.isoftston.issuser.fusioncharge.MainActivity;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.adapters.PayStyleAdpter;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.model.beans.HomeRefreshBean;
import com.isoftston.issuser.fusioncharge.model.beans.PayInfoBean;
import com.isoftston.issuser.fusioncharge.model.beans.PayStyleBean;
import com.isoftston.issuser.fusioncharge.presenter.PayPresenter;
import com.isoftston.issuser.fusioncharge.utils.alipay.AuthResult;
import com.isoftston.issuser.fusioncharge.utils.alipay.OrderInfoUtil2_0;
import com.isoftston.issuser.fusioncharge.utils.alipay.PayResult;
import com.isoftston.issuser.fusioncharge.views.interfaces.PayView;
import com.isoftston.issuser.fusioncharge.weights.CommonDialog;
import com.isoftston.issuser.fusioncharge.weights.NavBar;
import com.isoftston.issuser.fusioncharge.weights.PayFailDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/20.
 */

public class PayActivity extends BaseActivity<PayView,PayPresenter> implements PayView {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.list)
    NoScrollingListView listView;
    @Bind(R.id.tv_charger_enegry)
    TextView tv_charger_enegry;
    @Bind(R.id.tv_charger_fee)
    TextView tv_charger_fee;
    @Bind(R.id.tv_charge_service_fee)
    TextView tv_charge_service_fee;
    @Bind(R.id.tv_total_fee)
    TextView tv_total_fee;


    private PayStyleAdpter adapter;
    private Context context=PayActivity.this;
    private List<PayStyleBean> list;
    private CommonDialog commonDialog;
    private PayFailDialog dialog;
    private String orderNum;

    public static Intent getLauncher(Context context,String orderNum){
        Intent intent =new Intent(context,PayActivity.class);
        intent.putExtra("num",orderNum);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setNavTitle(getString(R.string.pay));
        nav.setImageBackground(R.drawable.nan_bg);

        orderNum=getIntent().getStringExtra("num");

        adapter=new PayStyleAdpter(context);
        list =new ArrayList<>();
        PayStyleBean bean =new PayStyleBean();
        bean.imgRes=R.mipmap.pay_money;
        bean.name=getString(R.string.pay_balance);
        bean.hint=getString(R.string.pay_balance_hint);
        bean.type="0";
        PayStyleBean bean1=new PayStyleBean();
        bean1.imgRes=R.mipmap.list_ic_weixin;
        bean1.name=getString(R.string.pay_wechat);
        bean1.hint=getString(R.string.pay_wechat_hint);
        bean1.type="1";
        PayStyleBean bean2=new PayStyleBean();
        bean2.imgRes=R.mipmap.list_ic_zhi;
        bean2.name=getString(R.string.pay_alipay);
        bean2.hint=getString(R.string.pay_alipay_hint);
        bean2.type="2";
        list.add(bean);
        list.add(bean1);
        list.add(bean2);

        adapter.addAll(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("yzh","---"+position);
                adapter.setCurrentPosition(position);
            }
        });

        commonDialog =new CommonDialog(context,"",getString(R.string.pay_balance_not_enough),1);
        dialog=new PayFailDialog(context);

        presenter.getPayDetailInfo(orderNum);
    }

    @Override
    protected PayPresenter createPresenter() {
        return new PayPresenter();
    }

    @OnClick(R.id.tv_pay)
    public void goPay(){
        //选中的支付类型
        String type=list.get(adapter.getCurrentPosition()).type;
        if(type.equals("0")){
//            commonDialog.show();
//            commonDialog.setDialogBackground();
            presenter.payAction(orderNum,payInfoBean.consumeTotalMoney,3);

        }else if(type.equals("1")){
//            dialog.show();
//            dialog.setTryAgainListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
        }else if(type.equals("2")){
//            startActivity(PayCompleteActivity.getLauncher(context));
        }
    }

    private void payForWechat(){
//        presenter.getPrepayID(orderId);
    }

//    @Override
//    public void renderPrepayId(PrepayBean bean) {
//        PayWithWechat payWithWechat=new PayWithWechat(contex,bean.prepayId);
//    }

    // 商户PID
    public static final String APPID = "2017102409494083";
    // 商户收款账号
//    public static final String SELLER = "296746476@qq.com";
    public static final String RSA_PRIVATE="";
    public  static final String RSA2_PRIVATE ="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCY5RoblyfsZsIe9vf" +
            "oHvCSAn3K3F9EbWtQVu1T2ZLXVfnLmMlaqzOCxYEDEQOwVv5dZOOxqxmG/G2zYUXM9ZKj/95kYUzsJHGYoakWdHYaW+u4FYoV3Fs0A6QAkXO3TwG7ToEgsKj/QvqabokD6Ct8zJn2EaGKV72G" +
            "cVZBigTGVXjVuy54FhmZla0LTHKGBAGuBOaMpsm7r2NAWjwmq7iq3iDOc5pPZvqYHmtYJ0AXe1" +
            "ASRrzkxNV861WPSkVuVM+3cYtAcAzlUUs9GV8L/SFMZLvFZd/iscjz6CFh06YGdxsH+S3OdWGyH3UCN3WvR3j" +
            "LAX0ciIrLEu3HHn/khIJdAgMBAAECggEAVDQQfdNwZExvalg/TbIEcZU3rApT2T6dlGBbUguISFmMOamKcwnIU6Ps" +
            "GkE7k9lFOzA+CfIeG+5XiCvxIiC4wuM+mm/PylmDyjh2A+zdwkSPfBE4vDNgczPyFXIFlZXIcVyZ1uQUHojHtVdc1C8A" +
            "DDouLIunOKZ7tVgj1fJqEMPRcTgtdzGnLM4uozNr5DN2OWHicAo7diXA1hrcu4rsuiDix5wziw6SmivbvQdmxZWkHV" +
            "iEd+m3P/iCTZYBn5WcQmxAdECTM3IVr4xjHwGALEkKbBwFZvKBojKZcNoUYDl/myUdYTSSgyFweLn2UJR6jiA8KJGsl3ir" +
            "qssTlwGPQQKBgQDx8xiSSk3bMkAzdAhOUQBZlpeMxPDGpjDs+rMD50CW5fMKGLMHCIGq8Pm/KK6m11cm0VnI3sq3wOg5q7" +
            "+pObfG8nTZy0Rijfxtef+tHdUUKGIOlM9oWYXuR50hF2FweUL2wUmAW5HUeS1jGQW2wuI1qM0Q2sJfh0SFwu938v0DMQK" +
            "BgQChxhbkN7fmPK6y+AS7M3cuUQi0j64oIk1Swm6Bxpo0b+K4SYmnngIwNI5VoghmNC/dyyU262KFVXsCtXF2+ncPgeSM" +
            "2vdPcJgP+XzDKblwIt/ElKvDUL3iLBUDbgF0Gcib8OpGQOgFqPvN2Ouefcj1H3J5xSQMSiU2xmEEriHu7QKBgCPhcDhpqogiOKEoyP" +
            "6MRqgCjDEFF8vt432RZDDk/5AhQ9ywcwRM/6XRHMh7iQMXFkacb1s5pwh/JDFBRigbdiSb2jZeTaLr+Y5mtEMBFJNrtbcB3IxWEmWW2ze" +
            "IFxwt739WhMbzTXocCl4S3OUQm6lnghnIx2CRV9kaYpcAQwNRAoGAF0b/iDsUNQbKRGo3lBAlt/5k+EYsUw4MfjoH2JiRG7Z1svqx2fJ8ch" +
            "8908iK2NntgUZ3xl08Qd844fzlpK8JbqvMDRB8wWt8FeBaOWLJkf8Sn6Cczm/RwreBFos/nksPeJDDxPQV7qqQAOCTlqyWu96YhDj/pN0OWc" +
            "FVuGyt6UkCgYBLM/9Bii8JQxMvTBStsTlL0/9a/5UpwP9NaVWBwEazXbe1C0zz8QIFQ4Z2pBhL+IHshJtaErJTYzVE0/bq07UvOZe/xOzZk" +
            "FddN4BcQvlLop99DzQtH0RBlihzcLCnPIjfc6sdkXoGdFMPZk+wlS4XrDkE1xkOnlcIy8N7qpGZwg==";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private void payForAli(){
        /**
         * 如果APPID是空值或私钥两个全是空，则弹出警告对话框告诉开发者“需要配置APPID|RSA_PRIVATE”
         */
        if (TextUtils.isEmpty(APPID) ||  TextUtils.isEmpty(RSA2_PRIVATE)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；防止本地orderInfo被认为更改，例如买个冰箱改成买个鸡蛋
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);//rsa2私钥已经被赋值
        //在这里，传入APPID和私钥，得到请求map，即包含支付订单信息的map
        String url = "http://m.zyh198.com/wy-app/"+"zhifubao/returnNativeServer";
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID,"", rsa2,"","","",url);
        //将map解析成一个String类型的支付订单
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        //是rsa2类型的私钥吗？如果rsa2私钥已经被赋值，那么privateKey就被设置成rsa2，否则就被设置成rsa。这就是支付宝说的“如果你有俩私钥，优先使用RSa2私钥，靠！”
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        //对，privateKey就是商户私钥！通过此方法对商户的私钥进行“签名”处理，处理后就会生成（返回）一个sign=GKHJL%……&*的一大串字串
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        //最终，结合orderparam参数与sign签名字串，搞成orderInfo字串；
        final String orderInfo = orderParam + "&" + sign;
        //新开一个线程，将orderInfo字串传入到PayTask任务中去
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                //新建一个PAyTask对象
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Log.e("TAG","resultInfo:"+resultInfo);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(PsPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        ToastMgr.show("支付成功");
//                        presenter.savealiPayData(peisongOrderBean);
//                        startActivity(PaySuccessActivity.getLauncher(context,"1"));

                    } else {
                        Log.e("TAG","resultInfo:"+resultInfo);
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastMgr.show("支付失败");
//                        startActivity(PaySuccessActivity.getLauncher(context,"2"));
//                        startActivity(PayResultActivity.getLaunchIntent(PsPayActivity.this,0));
//                        finish();
                    }


                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(context,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();

                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(context,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private PayInfoBean payInfoBean;

    @Override
    public void renderData(PayInfoBean bean) {
        this.payInfoBean=bean;
        tv_charger_enegry.setText(bean.chargePowerAmount+getString(R.string.du));
        tv_charger_fee.setText(bean.eneryCharge+getString(R.string.yuan));
        tv_charge_service_fee.setText(bean.serviceCharge+getString(R.string.yuan));
        tv_total_fee.setText("￥"+bean.consumeTotalMoney);
    }

    @Override
    public void paySuccess() {
        while(!AppManager.getAppManager().currentActivity().getClass().equals( MainActivity.class)){
            AppManager.getAppManager().finishActivity();
        }
        //给首页发送一个消息去掉未支付提示栏
        HomeRefreshBean bean =new HomeRefreshBean();
        bean.type=0;
        RxBus.getDefault().send(bean, Constant.HOME_STATUE_REFRESH);
        startActivity(PayCompleteActivity.getLauncher(context));
    }

    @Override
    public void payBalanceNotEnough() {
            commonDialog.show();
            commonDialog.setDialogBackground();
    }

    @Override
    public void goLogin() {

    }
}
