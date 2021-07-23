# UnityInAndroidDome
Unity打包到原生Android平台
	1. Unity项目打包注意事项：

	unity包名与Android项目的包名一致

	2. 然后打开 Android Studio，选择 File - New - Import Project，选择之前导出的 Unity 工程，把它倒入 Android Studio，具体如下图

	3. AndroidStudio工程具体修改:

	A. app添加unity-classes.jar包：

	B. Build.gradle 修改：

```javascript

plugins {
    id 'com.android.application'
}

android {
    compileSdkVersion 30
    buildToolsVersion '30.0.3'

    defaultConfig {
        minSdkVersion 24
        targetSdkVersion 30
        applicationId 'com.example.unitydome'
        ndk {
            abiFilters 'armeabi-v7a'
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    aaptOptions {
        noCompress = ['.ress', '.resource', '.obb'] + unityStreamingAssets.tokenize(', ')
        ignoreAssetsPattern = "!.svn:!.git:!.ds_store:!*.scc:.*:!CVS:!thumbs.db:!picasa.ini:!*~"
    }

    lintOptions {
        abortOnError false
    }

    buildTypes {
        debug {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt')
            signingConfig signingConfigs.debug
            jniDebuggable true
        }
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt')
            signingConfig signingConfigs.debug
        }
    }

}

dependencies {

    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'com.google.android.material:material:1.2.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation files('libs\\unity-classes.jar')
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'

    implementation project(':unityLibrary')
}

```


	C. MainActivity跳转Unity

```javascript

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, UnityForMainActivity.class);
        startActivity(intent);
    }

}


	D. 添加Unity返回app监听脚本：

public class UnityForMainActivity extends UnityPlayerActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_BACK){
            // 添加返回键返回 MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //销毁
            finish();
        }
        return super.onKeyDown(i, keyEvent);
    }

}

```

	E. AndroidManifest.xml 修改：

```xml

<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.unitydome">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.UnityDome">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <activity android:name=".UnityForMainActivity">
            <meta-data android:name="unityplayer.UnityActivity" android:value="true" />
        </activity>

        <activity android:name="com.unity3d.player.UnityPlayerActivity"
            android:theme="@style/UnityThemeSelector"
            android:screenOrientation="fullSensor"
            android:launchMode="singleTask"
            android:configChanges="mcc|mnc|locale|touchscreen|keyboard|keyboardHidden|navigation|orientation|screenLayout|uiMode|screenSize|smallestScreenSize|fontScale|layoutDirection|density"
            android:hardwareAccelerated="false">

            <meta-data android:name="unityplayer.UnityActivity" android:value="true" />
            <meta-data android:name="android.notch_support" android:value="true" />

        </activity>

    </application>

</manifest>

```

	F. values文件夹下添加 ids.xml，strings.xml， stylee.xml

	4. 项目结构预览


	5. 以上是unity项目导入AndroidStudio打包前的基本流程，所有工作做完后就可以直接打包调试了

