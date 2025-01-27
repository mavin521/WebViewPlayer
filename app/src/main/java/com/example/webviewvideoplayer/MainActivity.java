import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_STORAGE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 检查存储权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) 
                != PackageManager.PERMISSION_GRANTED || 
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) 
                != PackageManager.PERMISSION_GRANTED) {
            
            // 权限未授予，请求权限
            ActivityCompat.requestPermissions(this, 
                new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE, 
                               Manifest.permission.READ_EXTERNAL_STORAGE }, 
                REQUEST_STORAGE_PERMISSION);
        }

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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限授予成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "存储权限未授予", Toast.LENGTH_SHORT).show();
            }
        }
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
