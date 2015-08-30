CycleViewPager
---

### 系统要求

 android 4.0以上
  
  
### 快速使用

##### 1. 导入CycleViewPager项目：该项目是一个库，在对应项目的java build path中添加该项目

##### 2. 在layout中引入cn.androiddevelop.cycleviewpager.lib.CycleViewPager这个Fragment.

	  <fragment
        android:id="@+id/cycleViewPager"
        android:name="cn.androiddevelop.cycleviewpager.lib.CycleViewPager"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

##### 3. 在Activity(Fragment)中设置CycleViewPager的views以及滚动与轮播属性

	CycleViewPager cycleViewPager = (CycleViewPager) getFragmentManager()
				.findFragmentById(R.id.cycleViewPager);
	// 设置循环，在调用setData方法前调用
	cycleViewPager.setCycle(true);

	// 在加载数据前设置是否循环
	cycleViewPager.setData(views);

	// 设置自动播放
	cycleViewPager.setWheel(true);

### 例子

##### 项目中给出了6个例子，可以直接运行项目或者下载项目中得apk进行查看效果
#### 1. NoCycleTextView
###### *没有滚动的CycleTextView,与ViewPager相同*
#### 2. CycleTextView
###### *可以滚动的CycleTextView*
#### 3. WheelCycleTextView
######	*可以滚动与自动切换的CycleTextView*
#### 4. EventCycleTextView
######	*监听CycleTextView滚动事件*
#### 5. FixedCycleTextView
######	*固定高度的CycleTextView*
#### 6. NestedCycleTextView
######	*CycleTextView嵌套在ViewPager中,实现滚动CycleTextView时外层ViewPager不滚动*

### javadoc文档

 [javadoc](http://doc.codeboy.me/CycleViewPager/)

### 注意事项

- 设置是否循环需要在设置数据之前，即在setData前调用setCycle，CycleViewPager默认不循环。
- 外层有viewPager时，需要继承cn.androiddevelop.cycleviewpager.lib.BaseViewPager。
- 设置自动播放时，CycleViewPager自动为可循环滚动.滚动是轮播的基础。