package com.example.harry.customandroid.tabs.develop.xmlParse;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Image")
public class Image {
    @Element(name = "Name", required = false)
    public String name;
    @Element(name = "Type")
    public String type;
    @Element(name = "SortValue")
    public String sortValue;
}