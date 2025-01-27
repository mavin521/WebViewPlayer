package com.example.webviewvideoplayer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

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
        String savedUrl = prefs.getString(KEY_URL, "https://www.cctv.com");  // 默认 URL
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

        // 错误处理：检测 WebView 加载失败
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(MainActivity.this, "加载失败: " + description, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (findViewById(R.id.webView) != null) {
            ((WebView) findViewById(R.id.webView)).onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (findViewById(R.id.webView) != null) {
            ((WebView) findViewById(R.id.webView)).onResume();
        }
    }
}
