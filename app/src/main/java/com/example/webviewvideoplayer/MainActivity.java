package com.example.webviewvideoplayer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "WebViewPrefs";
    private static final String KEY_URL = "saved_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true); // 启用 JavaScript
        webView.setWebViewClient(new WebViewClient()); // 处理网页中的链接
        webView.setWebChromeClient(new WebChromeClient()); // 自动最大化视频播放

        // 获取存储的 URL，若没有则设置默认 URL
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedUrl = prefs.getString(KEY_URL, "https://www.example.com");  // 默认 URL
        webView.loadUrl(savedUrl);

        // 允许用户修改 URL
        EditText urlEditText = findViewById(R.id.urlEditText);
        urlEditText.setText(savedUrl);

        urlEditText.setOnEditorActionListener((v, actionId, event) -> {
            String newUrl = urlEditText.getText().toString();
            prefs.edit().putString(KEY_URL, newUrl).apply();  // 存储新的 URL
            webView.loadUrl(newUrl);
            return true;
        });
    }
}
