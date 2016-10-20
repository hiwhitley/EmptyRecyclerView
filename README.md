###一、前言
   最近在用RecyclerView的时候，竟然发现它不可以像以前使用listView或者gridView时，当列表为空时，显示一个特殊的empty view来提示用户。没关系，o(^▽^)o，我们可以自己来实现这个功能。
###二、效果展示
![](http://img.blog.csdn.net/20160502143528841)
###三、实现思路
   阅读RecyclerView的源码，可以发现里面自带了一个数据观察者AdapterDataObserver用来监听数据的变化（代码在下面），所以我们自然想到了，自定义一个RecyclerView，重写里面的这个类，然后检查数据源的个数是否为0，当为空的时候，把我们先显示的EmptyView显示出来，这个和以前ListView相似。思路比较简单，直接看看代码是怎么实现的。

------------


源码中AdapterDataObserver类

```java
/**
     * Observer base class for watching changes to an {@link Adapter}.
     * See {@link Adapter#registerAdapterDataObserver(AdapterDataObserver)}.
     */
    public static abstract class AdapterDataObserver {
        public void onChanged() {
            // Do nothing
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            // do nothing
        }

        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            // fallback to onItemRangeChanged(positionStart, itemCount) if app
            // does not override this method.
            onItemRangeChanged(positionStart, itemCount);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            // do nothing
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            // do nothing
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            // do nothing
        }
    }
```

###四、代码

EmptyRecyclerView.class

```java
package com.hiwhitley.potatoandtomato.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hiwhitley on 2016/4/28.
 */
public class EmptyRecyclerView extends RecyclerView {

    private View emptyView;
    private static final String TAG = "hiwhitley";

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            Log.i(TAG, "onItemRangeInserted" + itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
    }

    private void checkIfEmpty() {
        if (emptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible =
                    getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty();
    }
}
```
重写AdapterDataObserver，每次数据变化都会调用onChanged(),所以就在这里检查数据源是否为空，看一下checkIfEmpty()方法,很简单，就是通过Adapter的getCount来判断时候数据为空，空就显示我们定义的EmptyView。
最后在setAdapter的时候注册一下就可以了。

layout_empty_view.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="match_parent"
                android:layout_height="match_parent">

    <ImageView
        android:id="@+id/iv_empty"
        android:layout_width="70dp"
        android:layout_height="70dp"
        android:layout_centerInParent="true"
        android:src="@drawable/icon_empty"
        android:tint="#ABABAB"
        android:layout_marginBottom="10dp"
        />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/iv_empty"
        android:layout_centerInParent="true"
        android:gravity="center"
        android:text="暂时还没有内容哦"
        />

</RelativeLayout>

```
你可以自定义需要的EmptyView。
###五、总结和源码下载
