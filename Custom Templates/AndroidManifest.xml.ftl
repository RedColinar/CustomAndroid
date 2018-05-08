<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application>
        <activity android:name="${packageName}.tabs.${module}.${camelCaseToUnderscore(classToResource(activityClass))}.${activityClass}" />
    </application>
</manifest>
