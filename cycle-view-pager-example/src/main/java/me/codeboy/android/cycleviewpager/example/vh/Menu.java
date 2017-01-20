package me.codeboy.android.cycleviewpager.example.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.codeboy.android.cycleviewpager.example.R;

/**
 * vh
 * Created by yuedong.lyd on 2017-01-16.
 */
public class Menu extends RecyclerView.ViewHolder {
    public TextView menu;

    public Menu(View itemView) {
        super(itemView);
        menu = (TextView) itemView.findViewById(R.id.menu);
    }
}
