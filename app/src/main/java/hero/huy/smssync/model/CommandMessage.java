package hero.huy.smssync.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import hero.huy.smssync.util.Logger;
import hero.huy.smssync.util.StringPadding;

/**
 * Created by huybu on 9/6/2015.
 */
public class CommandMessage implements Serializable {
    private String mType;
    private String mContent;
    private String mPhoneNumber;
    private String mReceiAddress;

    public CommandMessage(String raw) {
        mType=raw.substring(0,CommandType.LENGTH_TYPE).trim();
        mPhoneNumber=raw.substring(CommandType.LENGTH_TYPE,CommandType.LENGTH_PHONE_NUMBER).trim();
        mContent=raw.substring(CommandType.LENGTH_TYPE+CommandType.LENGTH_PHONE_NUMBER).trim();
    }

    @Override
    public String toString() {
        return StringPadding.rightPad(mType,CommandType.LENGTH_TYPE)
                +StringPadding.rightPad(mPhoneNumber,CommandType.LENGTH_PHONE_NUMBER)
                +mContent;
    }

    public CommandMessage(String type,String phoneNumber, String content, String receiAddress) {
        mType = type;
        mContent = content;
        mPhoneNumber=phoneNumber;
        mReceiAddress = receiAddress;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public byte[] getBytes() {
        try {
            return this.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            Logger.e("CommandMessage.getBytes UnsupportedEncodingException");
            e.printStackTrace();
            return null;
        }
    }

    public String getReceiAddress() {
        return mReceiAddress;
    }

    public void setReceiAddress(String receiAddress) {
        mReceiAddress = receiAddress;
    }
    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

}
