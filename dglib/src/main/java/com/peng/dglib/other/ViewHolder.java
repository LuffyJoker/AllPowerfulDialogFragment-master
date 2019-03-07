package com.peng.dglib.other;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mr.Q on 2019/3/5
 * 描述：
 */
public class ViewHolder{

    private View rootView;
    private SparseArray<View> views = new SparseArray<>();

    public ViewHolder(View rootView) {
        this.rootView = rootView;
    }


    public  <T> T getView(@IdRes int viewId){
        if(views.get(viewId)!=null){
            return (T) views.get(viewId);
        }
        if(rootView.findViewById(viewId) != null){
            views.put(viewId, rootView.findViewById(viewId));
            return  (T)rootView.findViewById(viewId);
        }
        return null;
    }

    public ViewHolder setText(int viewId, CharSequence text) {
        if(getView(viewId) == null){
            throw new NullPointerException("没有找到这个 View");
        }else{
            TextView textView = getView(viewId);
            textView.setText(text);
        }
        return this;
    }

    /**
     *
     * @param viewId
     * @param visible 取值范围：View.VISIBLE、View.GONE、View.INVISIBLE
     * @return
     */
    public ViewHolder setVisible(int viewId, int visible) {
        if(getView(viewId)==null){
            throw new NullPointerException("没有找到这个 View");
        }else{
            View view = getView(viewId);
            view.setVisibility(visible);
        }
        return this;
    }

    /**
     *
     * @param viewId
     * @param colorRes 颜色为资源ID
     * @return
     */
    public ViewHolder setTextColor(int viewId,int colorRes) {

        if(getView(viewId)==null){
            throw new NullPointerException("没有找到这个 View");
        }else{
            TextView textView = getView(viewId);
            textView.setTextColor(colorRes);
        }
        return this;
    }

    /**
     *
     * @param viewId
     * @param imageRes 图片为资源ID
     * @return
     */
    public ViewHolder setImageResource(int viewId,int imageRes) {
        if(getView(viewId)==null){
            throw new NullPointerException("没有找到这个 View");
        }else{
            ImageView imageView = getView(viewId);
            imageView.setImageResource(imageRes);
        }
        return this;
    }

    /**
     * 设置 CheckBox 是否选中
     * @param viewId
     * @param isChecked
     * @return
     */
    public ViewHolder setChecked(int viewId,boolean isChecked){

        if(getView(viewId)==null){
            throw new NullPointerException("没有找到这个 View");
        }else{
            CheckBox checkBox = getView(viewId);
            checkBox.setChecked(isChecked);
        }
        return this;
    }

    public ViewHolder setOnClickListener(int viewId, View.OnClickListener clickListener) {

        if(getView(viewId)==null){
            throw new NullPointerException("没有找到这个 View");
        }else{
            View view = getView(viewId);
            view.setOnClickListener(clickListener);
        }
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId,View.OnLongClickListener clickListener){

        if(getView(viewId)==null){
            throw new NullPointerException("没有找到这个 View");
        }else{
            View view = getView(viewId);
            view.setOnLongClickListener(clickListener);
        }
        return this;
    }

    /**
     *
     * @param viewId
     * @param resId 背景资源文件
     * @return
     */
    public ViewHolder setBackgroundResource(int viewId, int resId){
        if(getView(viewId)==null){
            throw new NullPointerException("没有找到这个 View");
        }else{
            View view = getView(viewId);
            view.setBackgroundResource(resId);
        }
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int colorRes){
        if(getView(viewId)==null){
            throw new NullPointerException("没有找到这个 View");
        }else{
            View view = getView(viewId);
            view.setBackgroundColor(colorRes);
        }
        return this;
    }

}