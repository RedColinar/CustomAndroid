<?xml version="1.0"?>
<recipe>

<merge from="AndroidManifest.xml.ftl"
            to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />
<merge from="root/res/values/strings.xml.ftl"
            to="${escapeXmlAttribute(resOut)}/values/strings.xml" />

<instantiate from="root/src/app_package/Activity.java.ftl"
                to="${escapeXmlAttribute(srcOut)}/tabs/${module}/${camelCaseToUnderscore(classToResource(activityClass))}/${activityClass}.java" />
<instantiate from="root/res/layout/activity.xml.ftl"
                to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />

<open file="${escapeXmlAttribute(srcOut)}/${activityClass}.java" />
<open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />

</recipe>
