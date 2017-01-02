package main.util;



import com.qiniu.util.Auth;

/**
 * Created by Feng on 2016/8/17.
 */
public class Utils {


    public static String getToken()
    {

        String access_key="M69EWymSG8o0Y7Tl8T5iZ3pZlyceiCvMalynnli2";
        String secret_key="1g3e5MsedHliY86oSZSH9RXFAdTeX0eD3eb9L3_-";
        String bucketname="lizhibutian";
        Auth auth= Auth.create(access_key,secret_key);

        return auth.uploadToken(bucketname);
    }


}

