package ${packageName};

<#if applicationPackage??>
import ${applicationPackage}.R;
</#if>

public class ${activityClass} extends ${superClass} {
    @Override
    public int getLayoutId() {
        return R.layout.${layoutName};
    }

    @Override
    public int getTitleId() {
        return R.string.${activityToLayout(activityClass)}_title;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}