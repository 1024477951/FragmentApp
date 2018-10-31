package com.fragmentapp.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;

import com.fragmentapp.home.fragment.IMFragment;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * Created by liuzhen on 2017/11/10.
 */

public class TabHelper {

    /**
     * 稀疏数组,是Android内部特有的api,标准的jdk是没有这个类的.在Android内部用来替代HashMap<Integer,E>这种形式,使用SparseArray更加节省内存空间的使用,
     * SparseArray也是以key和value对数据进行保存,并且key不需要封装成对象类型,最大的优势就是内存消耗小
     * */
    private static SparseArray<SoftReference<IMFragment>> arr = new SparseArray<SoftReference<IMFragment>>();

    public static IMFragment getFragment(int position) {
        return arr.get(position).get();
    }

    public static void init(FragmentManager fm){
        for (Tabs tab : Tabs.values()) {
            try {
                IMFragment fragment = null;

                List<Fragment> fs = fm.getFragments();
                if (fs != null) {
                    for (Fragment f : fs) {
                        if (f.getClass() == tab.clazz) {
                            fragment = (IMFragment) f;
                            break;
                        }
                    }
                }

                if (fragment == null) {
                    fragment = tab.clazz.newInstance();
                }
                fragment.attachTabData(tab);

                arr.put(tab.tabIndex,new SoftReference<IMFragment>(fragment));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getCount(){
        return Tabs.values().length;
    }

    public interface OnListenerClick{
        void click();
    }

}
