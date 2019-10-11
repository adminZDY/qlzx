package com.alipay.config;

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

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016093000629524";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpAhJJX3pAXP4YslFvN+babxAe5ByFG644MQGZxeyes0v3ta3JeyTfSFMYGb1ednm8uQgKSSGjy7bzcssGkAASXaLcCgClLPo40+IbOnyi+lpXpUXxy7ruRBH8rODUhKWkWh/x8NOCahQsbyWAEGYFjKQVS0bNCjLueTKYN71vdd/i7uMsAaqpPGZCQ43LI4ECY4FUwIVlKiuf13pZtzUE+d/rgjPSa6r8LpwV/MUuo26c4IVUZNljKNP78fy3cayoW3O9++EAFIpA6ILmyL72H8dMPbQF20UBoMokmZb0eCz1n7zWD2JtTuOOWgpfJct+dCfoGz4AKX+/+Ues3i3DAgMBAAECggEAHrQTomTIDt1WBCKRDMV5GQHISfRjAgOPWQH+KnN8kzA7gtrsoYfPoQjAn8Y9UuXvI8vxm/zBMcKWkA+wtpZ+jNeZ+KOQjnFnW9+SWiP79JfKxdVuqYi7gwG2ytKVXcZjWUJOP2Pi6PRgidn/VA6mmid8v6mXSTSrIAvAl0q5DYBt/X9HlEAwNeefT7om36US8DlMnjJ2VmroS0SfhArfwoiAazzokYKV0ar1C4iPu940hVD3WgPG6nvaeRe/HPjxrK/f1MlFR8Oz2JLWdZ/P2EhIx9txiMA5eoxDC+avvS1wbckx5f3KyIBy3WeZGieR9+dQ61CH5rB9l4Wiiigr0QKBgQD1FDFXHYV9XJ6//D6hjulYEHfHRTs9WeYQEXYlgkOQfpS3LSCSfupEoBf2mc4MubN5eWNTWlp3ygIQhz7AYL9SGGLMdcx9tiUl4hv7aB3N8UkKdEHZQVEa5lUjosUpXk9YRPcvKIhuC0F+zHTZVhBEGa9poO4+NXYQWyt6NP3crwKBgQCwihRZG4Unnu2cI3+6WwTSse0rutLOJzEKseylX3aoPGxoAelPe0JI+51ndqS863/WydZ0yHnpkd4GIkz4CJne72fkdjfbrSqHwZs5mwBQnRyNc2TL12FkZx3GPYDAPgUadV1Y2BB2l2SpTuTWLKIw1r5GaeQ2CTg5VosaUe6NLQKBgAXuUStciYh5H1ZiElnyjW8WJu/pmv9nF4w3//X0axIqHgs4ylsBO0Yh4bKUEkp4jAFakK7JtvrDJr2jqD8bmEWyVWHBUAuSxt9d03OIF4COYEgifB132TCfdlwAamkP4Tevo5O+lV6kYxdgd0tzK72aKYaS4HEc9hh7PFgexC39AoGAaNW2e6vyCLNCGwShpwLB/HSYiO1qne18HAPIqfPkvMcVATaRXBewJzL0KCq2bVHUGrLFXZhnfSKYHEBmQKyq4057/ZmOVi+hH/kCutSm/TgvanhqFPqT5VGuxrhjKsYptaqYglfcfvAsYLZvAhiDQElluN7NHKGg97vC4yBgbwkCgYEAj4z6wKucJ8CgOC7Skxe2FaOGT+GyP4sBey9G7dtGjBJckjKZR2/3iPqtQNIJ3JyrlcyDw1zkrazKBPeg0SD9mQ4bLeGO4ErdpodVReJmuUT4WU8grzK7FVixeGAWnMct3fvHScgCsb6uAVwTkGB0WI4kJQVqafez+Ssh52pEfFM=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgZyAJ+/7bPdF2fL3fiIAbGUXaYT+u6Tcrp4jCbXzrgr3XWhRslHpM/pwFploqbSsDghUsoTEQRCBKk2FCWWgzXNZdWbGtOwnh7ORFIfmMeW7txzOLhlqwUS7oQVJ+Sc97DF8BgH2KSPxuAoU+xHvxmwJWNZU7gz7gsWsowQmakAF3JkynHaU29/a3Y1F0Zc8rxLBOKQc82LDKpOqLCMg9/0SPwg04dlKjcsucTcKFWxW4+S+U6ut5dahQDhUKkia8aWfxwm04lgw/azF9LzjfPckoLJ+V3Lf8t60LxbJwLyFNbh7qChSiPqEq9nfUNhr2nCRAg8J6i9+KWuqPvZKYwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/qlzx/pay/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/qlzx/pay/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	
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

