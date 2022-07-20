# CircleProgressView
##### 环形进度条显示效果图

![进度条效果](https://github.com/huihui2286433852/LabServiceWidget-master/blob/master/images/jindutiao.gif "inner_circle.png")

进度条效果

##### 添加工程

###### **Step 1.** Add the JitPack repository to your build file 

​	Add it in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```



###### **Step 2.** Add the dependency

```
dependencies {
	        implementation 'com.github.huihui2286433852:CircleProgressView:1.0.1'
	}
```



##### 使用方式，更多属性请参考表格属性



###### 使用的xml代码

```
<com.ilab.widgetlibrary.CircleProgressView
        android:id="@+id/progress_true"
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="50dp"
        app:cpv_background_circle_color="#40B9F9"
        app:cpv_background_circle_width="50dp"
        app:cpv_circle_color="#B052FF"
        app:cpv_circle_width="50dp"
        app:cpv_first_from_second="50"
        app:cpv_inner_circle_color="#8DDAFF"
        app:cpv_isProgressGradient="true"
        app:cpv_outside_ring_color="#FFFFFF"
        app:cpv_outside_ring_width="1dp"
        app:cpv_progressEnd="#0F031E"
        app:cpv_progressStart="#EF07D8"
        app:cpv_text_color="#FFFFFF"
        app:cpv_text_size="40"
        app:cpv_text_suffix="%"
        app:cpv_tip_text="已占用"
        app:cpv_tip_text_color="#FFFFFF"
        app:cpv_tip_text_size="26" />
```


###### 使用java代码

```
		CircleProgressView progressTrue=findViewById(R.id.progress_true);
        CircleProgressView progressFalse=findViewById(R.id.progress_false);
        progressTrue.setTextEnabled(true);
        progressFalse.setTextEnabled(true);

        progressTrue.setMaxProgress(100);
        progressFalse.setMaxProgress(100);
        progressTrue.setProgressWithAnimation(100,3000);
        progressFalse.setProgress(100);
```









###### 使用说明表

| java代码设置进度条属性   |                                                        |
| ------------------------ | ------------------------------------------------------ |
| setShowOutCircle         | 设置是否显示外环线                                     |
| setShowInnerCircle       | 设置是否显示内圆                                       |
| setProgressGradient      | 设置进度条颜色是否渐变， 渐变的进度范围是0-180,180-360 |
| setProgressStartColor    | 设置进度条开始的渐变颜色                               |
| setProgressEndColor      | 设置进度条结束的渐变颜色                               |
| setOutsideRingWidth      | 设置外环的宽度                                         |
| setOutsideRingColor      | 设置外环颜色                                           |
| setFirstFromSecond       | 设置外环到内环的距离                                   |
| setCircleWidth           | 设置圆弧的宽度                                         |
| setInnerCircleColor      | 设置内圆的颜色                                         |
| setCircleColor           | 设置圆弧的颜色                                         |
| setTextPrefix            | 设置文本的前缀内容                                     |
| setTextSuffix            | 设置文本的后缀内容                                     |
| setTextSize              | 设置文字的大小                                         |
| setTextBold              | 设置文字加粗                                         |
| setTextEnabled           | 设置文本可用（显示）状态                               |
| setStartAngle            | 设置开始绘画圆弧的位置                                 |
| setTextColor             | 设置文本字体的颜色                                     |
| setTipTextColor          | 设置提示文本字体颜色                                   |
| setTipTextSize           | 设置提示文本字体大小                                   |
| setTipText               | 设置提示文本内容                                       |
| setProgress              | 设置当前进度值                                         |
| setMaxProgress           | 设置最大进度值                                         |
| setProgressWithAnimation | 设置进度动画                                           |
| setBackgroundColor       | 设置圆弧背景颜色                                       |
| setBackgroundStrokeWidth | 设置圆弧背景的宽度                                     |



| xml文件设置进度条属性       |                          |
| --------------------------- | ------------------------ |
| cpv_text_prefix             | 设置文本的前缀内容       |
| cpv_text_suffix             | 设置文本的后缀内容       |
| cpv_progress                | 设置当前进度值           |
| cpv_max_progress            | 设置最大进度值           |
| cpv_circle_color            | 设置圆弧的颜色           |
| cpv_inner_circle_color      | 设置内圆的颜色           |
| cpv_outside_ring_color      | 设置外环颜色             |
| cpv_background_circle_color | 设置圆弧背景颜色         |
| cpv_text_color              | 设置文本字体的颜色       |
| cpv_text_size               | 设置文字的大小           |
| cpv_text_bold               | 设置文字的加粗           |
| cpv_tip_text_color          | 设置提示文本字体颜色     |
| cpv_tip_text_size           | 设置提示文本字体大小     |
| cpv_tip_text                | 设置提示文本内容         |
| cpv_circle_width            | 设置圆弧的宽度           |
| cpv_outside_ring_width      | 设置外环的宽度           |
| cpv_background_circle_width | 设置圆弧背景的宽度       |
| cpv_first_from_second       | 设置外环到内环的距离     |
| cpv_isShowOutCircle         | 设置是否显示外环线       |
| cpv_isShowInnerCircle       | 设置是否显示内圆         |
| cpv_isProgressGradient      | 设置进度条颜色是否渐变   |
| cpv_progressStart           | 设置进度条开始的渐变颜色 |
| cpv_progressEnd             | 设置进度条结束的渐变颜色 |




# SingleChoiceSpinner
##### 下拉单项选择框

![下拉选择框效果](https://github.com/huihui2286433852/LabServiceWidget-master/blob/master/images/xialakuang.gif "inner_circle.png")

###### 使用的xml代码

```
    <com.ilab.widgetlibrary.spinner.SingleChoiceSpinner
        android:id="@+id/mySpinner"
        android:layout_width="300dp"
        android:layout_height="70dp"
        app:hint="请选择"
        app:hint_color="#ff0000"
        app:text_color="#ff0000"
        app:text_size="24sp"
        app:arrow_color="#ff0000"
        app:text_bg="@drawable/aa"
        app:spinner_bg="@drawable/aa"
        app:spinner_text_padding="10dp"
        app:spinner_text_color="#ff0000"
        app:spinner_text_check_color="#ff0000" />
```

###### 使用kotlin代码

```
        val mySpinner = findViewById<SingleChoiceSpinner>(R.id.mySpinner)
        mySpinner.setData(mutableListOf("Java","Android","微信小程序","Flutter","ReactNative"))
        mySpinner.setTextColor(0xff00ff00.toInt())
        mySpinner.setTextSize(20f)
        mySpinner.setHint("嘻嘻嘻")
        mySpinner.setHintColor(0xff00ff00.toInt())
        mySpinner.setArrowColor(0xff00ff00.toInt())
        mySpinner.setTextBg(resources.getDrawable(R.drawable.bb))
        mySpinner.setSpinnerTextPadding(0f)
        mySpinner.setPopSpinnerBg(resources.getDrawable(R.drawable.bb))
        mySpinner.setPopSpinnerTextColor(0xff00ff00.toInt())
        mySpinner.setPopSpinnerTextCheckColor(0xff00ff00.toInt())
```


###### 使用说明表

| kotlin代码设置下拉选择属性   |                                                        |
| ----------------------------- | ------------------------------------------------------ |
| setData                       | 设置下拉框列表选项内容                   |
| setTextColor                  | 设置文本框字体颜色                     |
| setTextSize                   | 设置设置文本字体大小（文本框+下拉框文本） |
| setHint                       | 设置文本框没有内容时提示                  |
| setHintColor                  | 设置文本框提示文本颜色                      |
| setArrowColor                 | 设置设置箭头颜色                          |
| setTextBg                     | 设置设置文本框背景                         |
| setSpinnerTextPadding         | 设置设置文本框与底部下拉框间距               |
| setPopSpinnerBg               | 设置下拉框背景                           |
| setPopSpinnerTextColor        | 设置下拉框文本颜色                         |
| setPopSpinnerTextCheckColor   | 设置下拉框选中文本颜色                       |

| xml代码设置下拉选择属性             |                                                        |
| ----------------------------- | ------------------------------------------------------ |
| text_color                    | 设置文本框字体颜色                     |
| text_size                     | 设置设置文本字体大小（文本框+下拉框文本） |
| hint                          | 设置文本框没有内容时提示                  |
| hint_color                    | 设置文本框提示文本颜色                      |
| arrow_color                   | 设置设置箭头颜色                          |
| text_bg                       | 设置设置文本框背景                         |
| spinner_text_padding          | 设置设置文本框与底部下拉框间距               |
| spinner_bg                    | 设置下拉框背景                           |
| spinner_text_color            | 设置下拉框文本颜色                         |
| spinner_text_check_color      | 设置下拉框选中文本颜色                       |
