package com.example.harry.customandroid.tabs.develop.xmlParse;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;

public class XmlParseActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_xml_parse;
    }

    @Override
    public int getTitleId() {
        return R.string.xml_parse_title;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String xmlString0 = "<UserLoginResponse xmlns=\"http://android.goodjob.cn/\">\n"+
                "                          <UserLoginResult>true</UserLoginResult>\n" +
                "                       </UserLoginResponse>";
        LoginResponse l = XmlUtil.getEntity(xmlString0, LoginResponse.class);

        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<AgentImage>\n" +
                "  <Image>\n" +
                "    <Name>1</Name>\n" +
                "    <Type>png</Type>\n" +
                "    <SortValue>1</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>2</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>2</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>3</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>3</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>4</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>4</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>5</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>5</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>6</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>6</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>7</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>7</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>8</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>8</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>9</Name>\n" +
                "    <Type>png</Type>\n" +
                "    <SortValue>9</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>10</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>10</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>11</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>11</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>12</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>12</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>13</Name>\n" +
                "    <Type>png</Type>\n" +
                "    <SortValue>13</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>14</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>14</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>15</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>15</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>16</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>16</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>17</Name>\n" +
                "    <Type>png</Type>\n" +
                "    <SortValue>17</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>18</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>18</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>19</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>19</SortValue>\n" +
                "  </Image>\n" +
                "  <Image>\n" +
                "    <Name>20</Name>\n" +
                "    <Type>jpg</Type>\n" +
                "    <SortValue>20</SortValue>\n" +
                "  </Image>\n" +
                "</AgentImage>";

                Images images = XmlUtil.getEntity(xmlString, Images.class);
                if (images != null) return;
    }
}
