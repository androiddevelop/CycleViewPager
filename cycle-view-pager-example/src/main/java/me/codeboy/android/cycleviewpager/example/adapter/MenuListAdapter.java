package me.codeboy.android.cycleviewpager.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.codeboy.android.cycleviewpager.example.R;
import me.codeboy.android.cycleviewpager.example.vh.Menu;

/**
 * menu list adapter
 * Created by yuedong.lyd on 2017-01-16.
 */
public class MenuListAdapter extends RecyclerView.Adapter<Menu> {
    private LayoutInflater mInflater;
    private Context mContext;
    private String[] mMenus;
    private Class[] mActions;

    public MenuListAdapter(Context context, String[] menus, Class[] actions) {
        mContext = context;
        mMenus = menus;
        mActions = actions;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mMenus != null ? mMenus.length : 0;
    }

    @Override
    public void onBindViewHolder(final Menu holder, int position) {
        if (holder == null || mMenus == null) {
            return;
        }
        if (!TextUtils.isEmpty(mMenus[position])) {
            holder.menu.setText(mMenus[position]);
        }
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, mActions[holder.getAdapterPosition()]));
            }
        });
    }

    @Override
    public Menu onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.example_cycleviewpager_main_item, null);
        return new Menu(view);
    }
}
