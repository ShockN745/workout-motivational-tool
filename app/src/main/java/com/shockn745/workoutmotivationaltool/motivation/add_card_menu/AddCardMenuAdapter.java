package com.shockn745.workoutmotivationaltool.motivation.add_card_menu;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shockn745.workoutmotivationaltool.R;
import com.shockn745.workoutmotivationaltool.motivation.recyclerview.cards.CardInterface;

import java.util.ArrayList;

/**
 * Adapter to display items in the add card menu
 */
public class AddCardMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface AddCardFromCacheCallback {

        /**
         * Method used to add a previously removed card
         * @param viewType view type to retrieve card from cache
         */
        void addCardFromCache(int viewType);
    }

    private class VH extends RecyclerView.ViewHolder {
        public TextView textView;
        private int mCardViewType;

        public VH(View itemView,
                  final AddCardFromCacheCallback addCardFromCacheCallback,
                  final AddCardMenuCallbacks addCardMenuCallbacks) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.add_card_menu_list_item_text_view);

            // Set OnClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addCardFromCacheCallback.addCardFromCache(mCardViewType);
                    addCardMenuCallbacks.hideAddCardMenu();
                }
            });
        }

        public void setCardViewType(int mCardViewType) {
            this.mCardViewType = mCardViewType;
        }
    }

    // Stores the VIEW_TYPES of cards that have been dismissed
    private final ArrayList<Integer> mListDismissedCards;
    private final Activity mActivity;
    private final AddCardFromCacheCallback mAddCardFromCacheCallback;

    public AddCardMenuAdapter(ArrayList<Integer> listDismissedCards,
                              Activity activity,
                              AddCardFromCacheCallback addCardFromCacheCallback) {
        this.mListDismissedCards = listDismissedCards;
        mActivity = activity;
        mAddCardFromCacheCallback = addCardFromCacheCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.add_card_menu_list_item, parent, false);
        return new VH(itemView, mAddCardFromCacheCallback, (AddCardMenuCallbacks) mActivity);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Display text describing the card< to add
        String textToSet;
        int cardType = mListDismissedCards.get(position);

        switch (cardType) {
            case CardInterface.WEATHER_VIEW_TYPE:
                textToSet = mActivity.getString(R.string.add_card_menu_weather);
                break;

            case CardInterface.ROUTE_VIEW_TYPE:
                textToSet = mActivity.getString(R.string.add_card_menu_route);
                break;

            case CardInterface.CALORIES_VIEW_TYPE:
                textToSet = mActivity.getString(R.string.add_card_menu_calories);
                break;

            default:
                textToSet = "CardType Not Supported!";
        }

        ((VH) holder).textView.setText(textToSet);
        ((VH) holder).setCardViewType(cardType);
    }

    @Override
    public int getItemCount() {
        return mListDismissedCards.size();
    }

}
