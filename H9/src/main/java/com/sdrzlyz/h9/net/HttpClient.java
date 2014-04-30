package com.sdrzlyz.h9.net;

import com.sdrzlyz.h9.exception.POAException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sdrzlyz on 14-3-25.
 */
public class HttpClient {
    private int timeout = 15000;//默认延时15秒

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String request(String s, String s1) throws POAException {
        System.out.println("req:" + s1);
        System.out.println("service:" + s);

        byte[] arrayOfByte = requestByte(s, s1);
        try {
            String str = new String(arrayOfByte, "utf-8");
            System.out.println("res:" + str);
            return str;
        } catch (UnsupportedEncodingException e) {
            throw new POAException(19);
        }
    }


    public byte[] requestByte(String url, String s) throws POAException {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestProperty("content-type", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setReadTimeout(timeout);
            urlConnection.setConnectTimeout(timeout);
            urlConnection.getOutputStream().write(s.getBytes("utf-8"));

            System.out.println("ResponseCode:"+urlConnection.getResponseCode());
            System.out.println("ResponseMessage:"+urlConnection.getResponseMessage());

            if (urlConnection.getResponseCode() == 500)
                throw new POAException(19);
            if (urlConnection.getResponseCode() == 404)
                throw new POAException(19);
            if (urlConnection.getResponseCode() != 200)
                throw new POAException(19);

            InputStream inputStream = urlConnection.getInputStream();
            byte[] bytes = readStream(inputStream);
            inputStream.close();
            urlConnection.disconnect();
            return bytes;
        } catch (MalformedURLException localMalformedURLException) {
            throw new POAException(19);
        } catch (IOException localIOException) {
            throw new POAException(3);
        } catch (NullPointerException localNullPointerException) {
            throw new POAException(3);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

    }

    public byte[] readStream(InputStream inputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrayOfByte1 = new byte[1024];
        while (true) {
            int i = bufferedInputStream.read(arrayOfByte1);
            if (i == -1) {
                byteArrayOutputStream.flush();
                byte[] arrayOfByte2 = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                inputStream.close();
                return arrayOfByte2;
            }
            byteArrayOutputStream.write(arrayOfByte1, 0, i);
        }
    }

    public byte[] getDataFromURL(String s) throws POAException {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(s).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setReadTimeout(timeout);
            httpURLConnection.setConnectTimeout(timeout);
            if (httpURLConnection.getResponseCode() == 500)
                throw new POAException(19);
            if (httpURLConnection.getResponseCode() == 404)
                throw new POAException(19);
            if (httpURLConnection.getResponseCode() != 200)
                throw new POAException(3);
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] arrayOfByte = readStream(inputStream);
            inputStream.close();
            return arrayOfByte;
        } catch (MalformedURLException localMalformedURLException) {
            throw new POAException(19);
        } catch (IOException localIOException) {
            throw new POAException(3);
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
    }
}
