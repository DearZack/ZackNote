# ZackNote

new ：

navigationView.setItemTextColor(getResources().getColorStateList(R.color.button_text));
navigationView.setItemIconTintList(getResources().getColorStateList(R.color.button_text));
//        navigationView.setItemIconTintList(null); //不要统一的颜色


<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_checked="true"
        android:color="#ffff0000"/> 
    <item android:state_checked="false"
        android:color="#ff0000ff"/> 
    <item android:color="#ff000000"/> 
</selector>
