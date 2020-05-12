package com.weknowall.cn.wuwei.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User: laomao
 * Date: 2018-10-22
 * Time: 11-02
 */

public class ImageLoader2 {
    private static volatile ImageLoader2 mInstance;
    private ExecutorService mExecutorService;
    private Type mType;
    private Semaphore mSemaphore;
    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
    private LinkedList<Runnable> mTasks;
//    private LinkedHashMap<String,Runnable> mTasks;
    private LruCache<String, Bitmap> mLruCache;
    private Handler mHandler;
    private Handler mUiHandler;
    private long mKeepAliveTime=3;
    private TimeUnit mTimeUnit=TimeUnit.MINUTES;

    private ImageLoader2(int threadCount, Type type) {
        init(threadCount, type);
    }

    public static ImageLoader2 getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader2.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader2(5, Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    public enum Type {
        LIFO, FIFO
    }

    private void init(int threadCount, Type type) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Looper.prepare();

                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mExecutorService.execute(getTask());
//                        try {
//                            mSemaphore.acquire();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                    }
                };

                mSemaphorePoolThreadHandler.release();
                Looper.loop();
            }
        }).start();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
//        mExecutorService = Executors.newFixedThreadPool(threadCount);
        // 当前设备可用处理器核心数*2+1，能够让cpu的效率得到最大的执行
        int corePoolSize=Runtime.getRuntime().availableProcessors()*2+1;
        mExecutorService=new ThreadPoolExecutor(corePoolSize,corePoolSize,mKeepAliveTime,mTimeUnit
                ,new LinkedBlockingDeque<>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        mSemaphore = new Semaphore(threadCount);
        mTasks = new LinkedList<>();
        mType = type == null ? Type.LIFO : Type.FIFO;
    }

    /**
     * 将runnable添加到任务列表，并发送消息给子线程，让其去取出消息执行
     * 之所以它能发消息给子线程，是因为handler是在子线程创建的
     * <p>
     * 因为addTask是在主线程中调用的，所以可能存在子线程中mHandler还没有初始化，所以使用Semaphore.acquire
     * 当获取到的值小于等于0时，取不到信号量，所以不往下在这里等待
     * 当子线程中进行初始化了Handler之后，调用了Semaphore.release()，释放出一个信号量，
     * 因为开始创建的时候默认是0，这个时候acquire就可以去到一个信号量，就可以往下执行了
     *
     * @param path
     * @param runnable
     */
    private synchronized void addTask(String path, Runnable runnable) {
        if (mHandler == null) {
            try {
                mSemaphorePoolThreadHandler.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        mTasks.add(runnable);
        mHandler.sendEmptyMessage(1);
    }

    private synchronized Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTasks.removeLast();
        } else {
            return mTasks.removeFirst();
        }
    }

    public void loadImage(String path, ImageView imageView) {
        imageView.setImageBitmap(null);
        imageView.setTag(path);
        if (mUiHandler == null) {
            mUiHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    ImageView imageView = holder.imageView;
                    Bitmap bitmap = holder.bitmap;
                    String path = holder.path;
                    if (imageView.getTag().toString().equals(path)) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            };
        }

        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null) {
            sendRefreshImgMessage(path, imageView, bm);
        } else {
            // 添加任务到任务列表
            addTask(path,new Runnable() {
                @Override
                public void run() {
                    ImageSize imageSize = getImageViewSize(imageView);

                    Bitmap bitmap = decodeSampledBitmapFromResource(path, imageSize.width, imageSize.height);
                    addBitmapToLruCache(path, bitmap);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                    sendRefreshImgMessage(path, imageView, bitmap);

//                    mSemaphore.release();
                }
            });
        }
    }

    /**
     * 通过handler更新UI
     *
     * @param path
     * @param imageView
     * @param bm
     */
    private void sendRefreshImgMessage(String path, ImageView imageView, Bitmap bm) {
        ImgBeanHolder holder = new ImgBeanHolder();
        holder.imageView = imageView;
        holder.bitmap = bm;
        holder.path = path;
        Message message = Message.obtain();
        message.obj = holder;
        mUiHandler.sendMessage(message);
    }

    private void addBitmapToLruCache(String path, Bitmap bitmap) {
        if (bitmap != null)
            mLruCache.put(path, bitmap);
    }

    private Bitmap decodeSampledBitmapFromResource(String path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int width, int height) {
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int inSampleSize = 1;
        if (outWidth > width || outHeight > height) {
            int widthRatio = Math.round(outWidth * 1.0f / width);
            int heightRatio = Math.round(outHeight * 1.0f / height);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    private ImageSize getImageViewSize(ImageView imageView) {
        ImageSize imageSize = new ImageSize();

        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();

        int width = imageView.getWidth();
        if (width <= 0) {
            width = layoutParams.width;
        }
        if (width <= 0) {
            width = getImageViewFieldValue(imageView, "mMaxWidth");
        }
        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }

        int height = imageView.getHeight();
        if (height <= 0) {
            height = layoutParams.height;
        }
        if (height <= 0) {
            height = getImageViewFieldValue(imageView, "mMaxHeight");
        }
        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }

        imageSize.width = width;
        imageSize.height = height;

        return imageSize;
    }

    private int getImageViewFieldValue(ImageView imageView, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);

            int fieldInt = field.getInt(imageView);
            if (fieldInt > 0 && fieldInt < Integer.MAX_VALUE) {
                value = fieldInt;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    private Bitmap getBitmapFromLruCache(String path) {
        return mLruCache.get(path);
    }

    class ImgBeanHolder {
        public ImageView imageView;
        public String path;
        public Bitmap bitmap;
    }

    class ImageSize {
        public int width;
        public int height;
    }

}
