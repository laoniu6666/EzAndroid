package com.laoniu.ezandroid.utils.http;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class WKOkhttpClient {

    public static OkHttpClient client;

    public WKOkhttpClient(){
    }

    public static OkHttpClient getInstance(){
        if(null==client){
            synchronized (client) {
                if (null == client) {
                    X509TrustManager trustAllCert = new X509TrustManagerImpl();
                    SSLSocketFactory sslSocketFactory = new SSLSocketFactoryImpl(trustAllCert);
                    HostnameVerifier verifier = new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
//					HostnameVerifier hv= HttpsURLConnection.getDefaultHostnameVerifier();
//					hv.verify("",sslSession);
                            return true;
                        }
                    };

                    client = new OkHttpClient();
                    OkHttpClient.Builder builder = client.newBuilder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .sslSocketFactory(sslSocketFactory,trustAllCert)
                            .hostnameVerifier(verifier)
                            .dns(new MyDns());;
                    client = builder.build();
                }
            }
        }
        return client;
    }

}
