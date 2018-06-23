package com.example.harry.customandroid.tabs.develop.xmlParse;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class Images {
    @ElementList(type = Image.class, inline = true)
    public List<Image> image;
}


