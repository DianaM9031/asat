package com.asat.amesoft.asat.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Jorge on 20/04/2016.
 */
public class VolleySingleton {

    private static VolleySingleton sInstance=null;
    private RequestQueue requestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton(Context context){
        requestQueue = Volley.newRequestQueue(context);

        mImageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });

    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public static VolleySingleton getInstance(Context context){
        if(sInstance==null){
            sInstance=new VolleySingleton(context);
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
