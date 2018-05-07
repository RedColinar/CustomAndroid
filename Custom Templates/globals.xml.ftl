<?xml version="1.0"?>
<globals>
    <#include "../common/common_globals.xml.ftl" />

    <global id="superClass" type="string" value="BaseActivity"/>
    <global id="manifestOut" value="${manifestDir}" />
    <global id="srcOut" value="${srcDir}/${slashedPackageName(packageName)}" />
    <global id="resOut" value="${resDir}" />
</globals>
