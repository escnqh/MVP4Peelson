# MVP4Peelson
### 一个自用解决内存泄漏问题的MVP模式框架  
- - -
### 索引
-[Why](#Why)  

-[How](#How)  

-[参考书目](#参考)  

- - - 
<span id="Why">

### Why
`MVP`模式广泛运用于Android项目开发实战之中，具有易于维护、易于测试、松耦合、复用性高等特点，但是当`Presenter`进行一些耗时操作时，由于其持有了`View`的强引用，如果在耗时操作之前此`View`层被销毁会导致`Presenter`一直持有`View`对象而`View`对象无法收回，此时就发生了内存泄漏。  

而通常的解决办法是通过自己实现一个`ActivityCollector`来解决：

```java

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
```
在BaseActivity中可以手动：
```java
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    private ForceOffLineReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
```
当然这个方法很蠢。

<span id="How">

### How
现在有一个全新的思路来解决这一问题。就是通过弱引用和`Activity`、`Fragment`的声明周期来解决这个问题。  

首先建立一个`Presenter`抽象，命名为`BasePresenter`，它是一个泛型类，泛型类型为`View`角色需要实现的接口类型：
```java
public abstract class BasePresenter<V> {
    protected Reference<V> mViewRef;

    //与View建立关系
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    //获取View
    protected V getView() {
        return mViewRef.get();
    }

    //判断是否与View建立了关系
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    //与View解除关系
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

}
```
`BasePresenter`有4个方法，分别与`View`建立关联、解除关联、判断是否与`View`建立关联、获取`View`。`View`类型通过`BasePresenter`的泛型类型传递进来，`Presenter`对这个`View`持有弱引用。通常情况下这个`View`类型应该是实现了某个特定接口的`Activity`或者`Fragment`等类型。
然后就是利用BaseActivity基类来控制它与Presenter的关系。
```java
public abstract class BaseActivity<V, P extends BasePresenter<V>> extends Activity {
    protected P mPresenter;
    private static final String TAG = "BaseActivity";
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        //ButterKnife.bind(this);
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract P createPresenter();
}
```
`BaseActivity`含有两个泛型参数，第一个是`View`接口类型，第二个是`Presenter`的具体类型。通过泛型参数，会使得一些通用的逻辑可以被抽象到`BaseActivity`类中。例如，在`BaseActivity`的`onCreate`函数中，会通过`createPresenter`函数创建一个具体的`Presenter`，这个`Presenter`的类型就是`BasePresenter<V>`类型。构建`Presenter`之后会调用`attachView`函数与`Activity`建立关联。而在`onDestroy`函数中会与`Activity`解除关联，从而避免内存泄漏。那么，如果在`onDestroy`中解除了对`Activity`的引用那么就没有必要再用弱引用了，其实，并不是在任何情况下`Activity`的`onDestroy`方法都会被调用，一旦这种情况发生，弱引用也会保证不会造成内存泄漏。而通过`BaseActivity`的封装维护`Presenter`与`View`关联关系的代码，使得子类可以避免重复的代码。  

例如PhotoInListFragment的代码实现如下：
```java
public class PhotoInListFragment extends BaseFragment<PhotoInfoContract.View<PhotoInfo>,PhotoInfoPresenter> implements PhotoInfoContract.View<PhotoInfo>,PhotoInfoContract.RefreshPhotoInfoListener{

    private String param;

    public PhotoInListFragment(){}

    public static PhotoInListFragment newInstance(String param){
        PhotoInListFragment fragment=new PhotoInListFragment();
        Bundle args = new Bundle();
        args.putString("param", param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(getArguments()!=null){
            param=getArguments().getString("param");
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_photoinfoinlist,container,false);
        initView(v);
        return v;
    }

    private void initView(View v) {

    }

    @Override
    public void refreshPhotoInfoList(ArrayList<PhotoInfo> photoInfoList) {

    }

    @Override
    protected PhotoInfoPresenter createPresenter() {
        return new PhotoInfoPresenter(param,this);
    }

    @Override
    public void onPhotoInfoRefresh() {

    }
}
```
此时，`Presenter`的创建以及与`View`建立关联等操作都被封装到`BaseFragment`中，清除了子类重复代码同时又避免了`Fragment`（`View`）内存泄漏问题。  

基于此实例请参照`Demo`。
如有问题请在`Issues`中提出，或者与我联系。

<span id="参考">

### 参考

《Android源码设计模式 实战与解析（第2版）》
