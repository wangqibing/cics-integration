package com.ccic.util;

import com.ccic.enums.ResultEnum;
import com.ccic.enums.UserEnum;
import com.ccic.exception.IntegrationException;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.security.MessageDigest;
/**
 * Created by 8000600758 on 2018/9/17.
 */
public class KeyUtil {
    private static Long serialNumber =0L;
    private final static int SERIAL_NUMBER_LENGTH = 8;

    private KeyUtil(){}
    /**
     * 生成主键 时间加随机数
     * @return
     */
    public  static synchronized String getUniqueKey(){
        StringBuilder uniqueKey = new StringBuilder();
        uniqueKey.append(System.currentTimeMillis());
        uniqueKey.append(new Random().nextInt(100000)+100000);
         return uniqueKey.toString();
    }

    public  static synchronized String getUserUniqueKey(UserEnum userEnum) throws Exception {
        serialNumber++;
        return userEnum.getCode()+getSerialNumber();
    }

    public  static synchronized String getQuestionUniqueKey(UserEnum userEnum){
        return userEnum.getCode()+getUniqueKey();
    }

    public static void main(String[] args){
        System.out.println(encodePassword("wwwbai222222ducom"));
    }

    public static String personalFormatDate(long times,String format){
        return new SimpleDateFormat(format).format(times);
    }

    public static synchronized String encodePassword(String password){

                if(null == password || "".equals(password)){
                    throw new IntegrationException(ResultEnum.USER_LOG_ERROR);
                }
                StringBuilder decodePWD = new StringBuilder(32);
                try {
                        MessageDigest md = MessageDigest.getInstance("SHA-256");
                    md.update(password.getBytes());
                    byte[] byteBuffer = md.digest();
                    for (int i = 0; i < byteBuffer.length; i++) {
                        String hex = Integer.toHexString(0xff & byteBuffer[i]);
                        if (hex.length() == 1) {
                            decodePWD.append('0');
                        }
                        decodePWD.append(hex);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return decodePWD.toString();
    }

    private static synchronized  String getSerialNumber() throws Exception {
        String strSerialNumber = serialNumber.toString();
        if(null != serialNumber){
            int  supplyLenth = SERIAL_NUMBER_LENGTH - serialNumber.toString().length();
            if(supplyLenth > 0){
                for(int i=0;i<supplyLenth;i++){
                    strSerialNumber = "0"+strSerialNumber;
                }
                return strSerialNumber;
            }else if(supplyLenth == 0){
                return strSerialNumber;
            }else{
                throw new Exception("系统流水号长度不够");
            }
        }else {
            throw new Exception("系统异常");
        }

    }
}
