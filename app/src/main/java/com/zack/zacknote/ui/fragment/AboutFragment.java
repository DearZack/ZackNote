package com.zack.zacknote.ui.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zack.zacknote.R;

/**
 * Created by Zack Zhou on 2016/6/4.
 */
public class AboutFragment extends Fragment {

    private TextView personalAddress, githubAddress, githubIssues;

    public AboutFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        personalAddress = (TextView) view.findViewById(R.id.about_app_personal_address);
        githubAddress = (TextView) view.findViewById(R.id.about_app_github_address);
        githubIssues = (TextView) view.findViewById(R.id.about_app_github_issues);
        String personal = "作者Zack：";
        String address = "项目地址：";
        String issues = "反馈建议：";
        personal += "<a href='https://github.com/DearZack'>GitHub</a> ";
        personal += "<a href='http://dearzack.github.io' >Blog</a> ";
        personal += "<a href='http://weibo.com/2867897514/profile?rightmod=1&wvr=6&mod=personinfo&is_all=1' >微博</a> ";
        personal += "<a href='https://www.zhihu.com/people/zhou-shi-kong' >知乎</a> ";
        address += "<a href='https://github.com/DearZack/ZackNote'>ZackNote</a>";
        issues += "<a href='https://github.com/DearZack/ZackNote/issues'>Issues</a>";
        CharSequence personalCharSequence = Html.fromHtml(personal);
        personalAddress.setText(personalCharSequence);
        personalAddress.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence addressCharSequence = Html.fromHtml(address);
        githubAddress.setText(addressCharSequence);
        githubAddress.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence issuesCharSequence = Html.fromHtml(issues);
        githubIssues.setText(issuesCharSequence);
        githubIssues.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }
}
