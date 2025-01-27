# 基础规则：保留应用主入口类（MainActivity）和相关配置
-keep class com.example.webviewvideoplayer.MainActivity {
    *;
}

# 保留所有 R 文件
-keep class **.R$* {
    *;
}

# 禁止优化某些类，避免构建错误
-dontoptimize
