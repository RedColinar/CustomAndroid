package ${packageName}.tabs.${module}.${camelCaseToUnderscore(classToResource(activityClass))};

<#if packageName??>
import ${packageName}.R;
</#if>
import com.example.harry.customandroid.base.BaseActivity;

public class ${activityClass} extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.${layoutName};
    }

    @Override
    public int getTitleId() {
        return R.string.${camelCaseToUnderscore(classToResource(activityClass))}_title;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}