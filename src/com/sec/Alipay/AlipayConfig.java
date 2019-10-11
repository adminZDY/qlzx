package com.sec.Alipay;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号(gai)
	public static String app_id = "2016093000629524";
	
	// 商户私钥，您的PKCS8格式RSA2私钥(gai)
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCX6uU7Jtc+krQAkdcUQkklFXOjBCHGKWP842C8i4xzxNU0lMHQ5GuXegDrIAYbaepxVA32Dgo52m/XwuUdPdIQgj5QPtjWPNaKM3sXqfvm3meuLdzwo0wB8T75uqF1soRzU859gYVe6k+ZOexeU5Ru1Ww9+yctwoXRHdrVcPNNnGoOnN7R1aDee9SqHf09l6U7O/lvHcT8Gd1JNH+1P39l39zh+Dd19JyED5AzPMcK/dNGccwL+9UqrukCOcnLMkLyAO902gGc4i+7SkNogt4dwK6IdTbiVsdyQJj2ox6C8rKk4RkFa3Duxd7axORo3gM7Vu+f+GcRhdDzjv8ItaHVAgMBAAECggEAB0dV3FOsFG9q+GwEZhAW+RPiCnl0NM5e4Ar045O8Ev/nRzGpZ5zKGuPZj2plYILenLduQ8/AZa01lpzb5V7E5c2XtriMIxefH9f/mc6nJxSY2xuPbmgkiCNK5SQwADehaPUm+5RtE3MehTTlQk+22OiY2w9hfSvIxZAsn/VdcuZssVpQKlAciKmL++wBuJpVy+KuwtRd3RuZ+m+44Y7N5mGyf4kKigLtTJnX2EVkQFGbyIxHkWaxU3FAC8U2qehFb6UMRP3G5ynqVhLV9mPuKOMM9DyVmaSA/WuNw37t1Ob5662gaFztQXhE+BueuKCxkHjXddd1PZG9qtZFZ9xmsQKBgQDQXSfKut6Y4ha2kuho4qh5uuvwCZoX4q25hpWsbK/+IArFlUTgv2gOkdFgUdRU5Jza+Q1s1C2VlE2Znqz0jSu6teW4SSztuKnfZr0Dg/sDTywi0k9XdmmNARLJotDtcdXBw7bIx8R4IIQP6fOtyt+zYLrISjmQTg4Gl+y8SUihTwKBgQC6ph/WuqbmqL1xIVHRSfjB/DJ4enTxyg6eihBebhbjAq+EHxUBBazlYiuP6XXGJearKdcnpDev7EOs5oun++k8QcYbSNwL/PAewwJSYZq5egSUP6SU4sl3mES0Vl+eAL3Pub4JT85Mv3UiuK1vqsJ0SKP8ZSk6JQ8rfjT+FwDZmwKBgQCL/gq+Ks9j6INaJ1n/HkoTf4MFw23EnogaHlJxhpS4KM8T8TZVykQfhdkm3dPLNc4tUSez9tqppPp+PB34MOEwkQXGy2NkVK3NazmTIxmDH66HtsWX/fbsxcvZrYqx/cmgZd5pB8745t/T0VvnaGbkHhlgeH40djz2MRzdZF9oAQKBgEW9InJR5WCTtczaXT6su+SHPbhkUOOy0omMHpeZ3LVPYJMmJevu2e4PIyWvXlD+AlT72ic2pZP23EZzEcVfS4M3NDUnoQhdpr5nsKtlSrF4lNNrgCwdHJex7iHPkARyVHmT9hx2lCd69eJWTH9Ht0ZJifQZPDE8fZGLo3M41xl3AoGBAJc76F96ZQ06oq7JLifKvGqsxhlKBc7EjThs/5jHeZfqRyV3KTja+XyCy4CK+31Gk3XevJ4rg6oz/yiuMG4LWAxg8xQO2AOEyNb0fCe/5KUJIexvZO8AT7H705+lcsqn6mULCO/nt/pX/pONCy3m3pfRnULaVvBZdOc9PUcKcRLU";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。(gai)
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgZyAJ+/7bPdF2fL3fiIAbGUXaYT+u6Tcrp4jCbXzrgr3XWhRslHpM/pwFploqbSsDghUsoTEQRCBKk2FCWWgzXNZdWbGtOwnh7ORFIfmMeW7txzOLhlqwUS7oQVJ+Sc97DF8BgH2KSPxuAoU+xHvxmwJWNZU7gz7gsWsowQmakAF3JkynHaU29/a3Y1F0Zc8rxLBOKQc82LDKpOqLCMg9/0SPwg04dlKjcsucTcKFWxW4+S+U6ut5dahQDhUKkia8aWfxwm04lgw/azF9LzjfPckoLJ+V3Lf8t60LxbJwLyFNbh7qChSiPqEq9nfUNhr2nCRAg8J6i9+KWuqPvZKYwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/qlzx/pay/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/qlzx/pay/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关(gai)
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

