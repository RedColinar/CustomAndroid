package com.example.harry.customandroid.tabs.develop.xmlParse;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class LoginResponse {
    @Element(name = "UserLoginResult")
    public String result;
}
