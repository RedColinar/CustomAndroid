package com.example.harry.customandroid.tabs.develop.greendao;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.UUID;

/**
 * @author panqiang
 * @version 1.0
 * @date 2018/7/22 19:41
 * @description
 */
public class UUID2BytesConverter implements PropertyConverter<UUID, byte[]> {
    @Override
    public UUID convertToEntityProperty(byte[] databaseValue) {
        return ConvertBytesToUUID(databaseValue);
    }

    @Override
    public byte[] convertToDatabaseValue(UUID entityProperty) {

        String str = ConvertUUIDToHexString(entityProperty);
        str = str.replace("X","").replace("'","");
        byte[] bytes = new byte[16];
        for(int i = 0; i<str.length();i=i+2){
            bytes[i/2] = ConvertHexStringToByte(str.substring(i, i+2));
        }
        return bytes;
    }

    public static String ConvertUUIDToHexString(UUID uuid) {
        if (uuid == null)
            return "null";
        String strLeast = String
                .format("%016x", uuid.getLeastSignificantBits());
        String strMost = String.format("%016x", uuid.getMostSignificantBits());

        char[] chMost = new char[16];
        chMost[0] = strMost.charAt(6);
        chMost[1] = strMost.charAt(7);
        chMost[2] = strMost.charAt(4);
        chMost[3] = strMost.charAt(5);
        chMost[4] = strMost.charAt(2);
        chMost[5] = strMost.charAt(3);
        chMost[6] = strMost.charAt(0);
        chMost[7] = strMost.charAt(1);
        chMost[8] = strMost.charAt(10);
        chMost[9] = strMost.charAt(11);
        chMost[10] = strMost.charAt(8);
        chMost[11] = strMost.charAt(9);
        chMost[12] = strMost.charAt(14);
        chMost[13] = strMost.charAt(15);
        chMost[14] = strMost.charAt(12);
        chMost[15] = strMost.charAt(13);

        return String.format("X'%s'", String.valueOf(chMost) + strLeast);
    }

    private byte ConvertHexStringToByte(String s) {
        if ("0x".equals(s.substring(0, 2))) {
            s = s.substring(2);
        }
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baKeyword[0];
    }

    private UUID ConvertBytesToUUID(byte[] bytes) {
        if (bytes == null)
            return null;
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return ConvertHexStringToUUID(sb.toString());
    }

    public static UUID ConvertHexStringToUUID(String str) {
        if (str == null)
            return null;
        str = str.toLowerCase().replace("x", "").replace("'", "");
        if (str.length() != 32)
            return null;
        String strMost = str.substring(0, 16);
        char[] chMost = new char[16];
        chMost[0] = strMost.charAt(6);
        chMost[1] = strMost.charAt(7);
        chMost[2] = strMost.charAt(4);
        chMost[3] = strMost.charAt(5);
        chMost[4] = strMost.charAt(2);
        chMost[5] = strMost.charAt(3);
        chMost[6] = strMost.charAt(0);
        chMost[7] = strMost.charAt(1);
        chMost[8] = strMost.charAt(10);
        chMost[9] = strMost.charAt(11);
        chMost[10] = strMost.charAt(8);
        chMost[11] = strMost.charAt(9);
        chMost[12] = strMost.charAt(14);
        chMost[13] = strMost.charAt(15);
        chMost[14] = strMost.charAt(12);
        chMost[15] = strMost.charAt(13);
        strMost = String.valueOf(chMost);
        String result = String.format("%s-%s-%s-%s-%s",
                strMost.substring(0, 8), strMost.substring(8, 12),
                strMost.substring(12, 16), str.substring(16, 20),
                str.substring(20));
        return UUID.fromString(result);
    }

}