CycleViewPager
---

### 系统要求

 android 4.0(含)以上，Demo地址:[https://github.com/androiddevelop/CycleViewPager/raw/master/cycle-view-pager-example-debug.apk](https://github.com/androiddevelop/CycleViewPager/raw/master/cycle-view-pager-example-debug.apk)

 ![](./demo_download_url.png)

### 快速使用

#### 1. 导入CycleViewPager项目

	 compile 'me.codeboy.android:cycle-view-pager:2.0.0'

#### 2. 在layout中引入me.codeboy.android.cycleviewpager.CycleViewPager这个View.

	  <me.codeboy.android.cycleviewpager.CycleViewPager
          android:id="@+id/cycleViewPager"
          android:layout_width="match_parent"
          android:layout_height="match_parent"/>

#### 3. views创建

    // 需要循环的创建方式
     List<View> views = new ArrayList<View>();

    views.add(ViewFactory.create("最后一个view"));
    for (int i = 0; i < 4; i++) {
        views.add(ViewFactory.create("第i个view");
    }
    views.add(ViewFactory.create("第一个view"));

     // 不需要循环的创建方式
     List<View> views = new ArrayList<View>();

     for (int i = 0; i < 4; i++) {
         views.add(ViewFactory.create("第i个view");
     }

#### 4. 设置CycleViewPager的views以及滚动与轮播属性

	CycleViewPager cycleViewPager = (CycleViewPager)mContext.findFragmentById(R.id.cycleViewPager);


    //以下setData参数含义：
    //1: 试图 2: 循环(默认true) 3: 轮播(默认false) 4: 轮播时间(默认5000ms)

	//循环+轮播，3000ms自动切换
	cycleViewPager.setData(views, true, true, 3000);

	//只循环，不轮播
	cycleViewPager.setData(views, true, false);
	cycleViewPager.setData(views, true);
	cycleViewPager.setData(views);

    //与viewPager相同
	cycleViewPager.setData(views, false);

> setData请放在最后一步设置。

### 例子

##### 项目中给出了6个例子，可以直接运行项目或者下载项目中得apk进行查看效果
#### 1. NoCycleTextView
###### *没有滚动的CycleTextView,与ViewPager相同*
#### 2. CycleTextView
###### *可以滚动的CycleTextView*
#### 3. WheelCycleTextView
######	*可以滚动与自动切换的CycleTextView*
#### 4. EventCycleTextView
######	*监听CycleTextView滚动事件和view的点击事件*
#### 5. IndicatorCustom1CycleTextView
######	*指示器的颜色、形状、位置的简单变化*
#### 6. IndicatorCustom2CycleTextView
######	*自定义的指示器*
#### 7. NestedCycleTextView
######	*CycleTextView嵌套在ViewPager中,实现滚动CycleTextView时外层ViewPager不滚动*

### javadoc文档

 [在线api文档](http://doc.codeboy.me/CycleViewPager/)

### 注意事项

- 外层有viewPager时，需要继承me.codeboy.android.cycleviewpager.BaseViewPager。
- 设置自动播放时，CycleViewPager自动为可循环滚动.滚动是轮播的基础。
- 自定义指示器时，获取的指示器容器的margin和padding都是0，需要自行根据需要设置。
- 控件的点击事件请自行设置，多增加的第一个和最后一个控件由于动画时间也请设置事件。CycleViewPager仅提供循环和轮播的机制。
- 使用中可以忽略添加的两个控件，listener中返回的position为处理后的位置。如果有4个view，名字为view0、view1、view2、view3,设置循环时，传入的views为view3'、view0、view1、view2、view3、view1'，返回的position将介于[0,3]之间。


## License

```
Copyright 2016 Yuedong.li

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

> 有任何问题,欢迎发送邮件到app@codeboy.me交流.