package com.shockn745.moovin5.motivation.recyclerview.cards;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.shockn745.moovin5.R;

/**
 * Card that display route
 *
 * @author Florian Kempenich
 */
public class CardRoute extends AbstractCard {

    private static final String LOG_TAG = CardRoute.class.getSimpleName();


    public static class RouteVH extends RecyclerView.ViewHolder {
        public FrameLayout mFrameLayout;

        public RouteVH(View itemView) {
            super(itemView);
            this.mFrameLayout = (FrameLayout) itemView.findViewById(R.id.route_frame_layout);
        }
    }


    public CardRoute(Activity activity) {
        super(activity);
    }

    @Override
    public int getViewType() {
        return ROUTE_VIEW_TYPE;
    }

    @Override
    public boolean canDismiss() {
        return true;
    }

}
