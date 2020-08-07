package com.laoniu.ezandroid.utils.http;

import org.jetbrains.annotations.NotNull;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Dns;
import okhttp3.OkHttpClient;

public class WKOkhttpClient {

    public static OkHttpClient client;

    public WKOkhttpClient(){
    }

    public static OkHttpClient getInstance(){
        if(null==client){
            synchronized (client) {
                if (null == client) {
                    X509TrustManager trustAllCert = getX509TrustManager();
                    SSLSocketFactory sslSocketFactory = new SSLSocketFactoryImpl(trustAllCert);

                    client = new OkHttpClient();
                    OkHttpClient.Builder builder = client.newBuilder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .sslSocketFactory(sslSocketFactory,getX509TrustManager())
                            .hostnameVerifier(getHostnameVerifier())
//                            .addInterceptor(new MyInterceptor())
                            .dns(getDNS());
                    client = builder.build();
                }
            }
        }
        return client;
    }

    private static HostnameVerifier getHostnameVerifier(){
        return new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
    }

    private static X509TrustManager getX509TrustManager(){
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    private static Dns getDNS(){
        return new Dns() {
            @NotNull
            @Override
            public List<InetAddress> lookup(@NotNull String hostname) throws UnknownHostException {
                try {
                    List<InetAddress> mInetAddressesList = new ArrayList<>();
                    InetAddress[] mInetAddresses = InetAddress.getAllByName(hostname);
                    for (InetAddress address : mInetAddresses) {
                        if (address instanceof Inet4Address) {
                            mInetAddressesList.add(0, address);
                        } else {
                            mInetAddressesList.add(address);
                        }
                    }
                    return mInetAddressesList;
                } catch (NullPointerException var4) {
                    UnknownHostException unknownHostException = new UnknownHostException("Broken system behaviour");
                    unknownHostException.initCause(var4);
                    throw unknownHostException;
                }
            }
        };
    }

}
