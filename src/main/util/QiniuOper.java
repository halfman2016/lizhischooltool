package main.util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

public class QiniuOper {
    private String accessKey;
    private String secretKey;
    private String bucket;

    public QiniuOper() {
        accessKey = "M69EWymSG8o0Y7Tl8T5iZ3pZlyceiCvMalynnli2";
        secretKey = "1g3e5MsedHliY86oSZSH9RXFAdTeX0eD3eb9L3_-";
        bucket = "lizhibutian";
    }

    public void getFiles(){
        Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释

        String accessKey = "M69EWymSG8o0Y7Tl8T5iZ3pZlyceiCvMalynnli2";
        String secretKey = "1g3e5MsedHliY86oSZSH9RXFAdTeX0eD3eb9L3_-";
        String bucket = "lizhibutian";
        String key = "your file key";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            FileInfo fileInfo = bucketManager.stat(bucket, key);
            System.out.println(fileInfo.hash);
            System.out.println(fileInfo.fsize);
            System.out.println(fileInfo.mimeType);
            System.out.println(fileInfo.putTime);
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
    }
}
