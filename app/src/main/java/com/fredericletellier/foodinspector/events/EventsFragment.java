/*
 *     Food Inspector - Choose well to eat better
 *     Copyright (C) 2016  Frédéric Letellier
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.fredericletellier.foodinspector.events;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fredericletellier.foodinspector.R;
import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.Product;

import static com.google.common.base.Preconditions.checkNotNull;

public class EventsFragment extends Fragment implements EventsContract.View {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private EventsContract.Presenter mPresenter;

    private EventsCursorAdapter mListAdapter;
    private View mNoEventsView;
    private ImageView mNoEventIcon;
    private TextView mNoEventMainView;
    private ListView mListEventView;

    /**
     * Listener for clicks on events in the ListView.
     */
    EventItemListener mItemListener = new EventItemListener() {
        @Override
        public void onEventClick(Event clickedEvent) {
            mPresenter.openEventDetails(clickedEvent);
        }

        @Override
        public void onErrorEventClick(Event clickedErrorEvent) {
            //no-op
        }
    };

    public EventsFragment() {
        // Requires empty public constructor
    }

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull EventsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.events_frag, container, false);

        // Set up events view
        mListAdapter = new EventsCursorAdapter(getActivity(), mItemListener);
        mListEventView = (ListView) root.findViewById(R.id.events_list);
        mListEventView.setAdapter(mListAdapter);

        // Set up  no tasks view
        mNoEventsView = root.findViewById(R.id.noEvents);
        mNoEventIcon = (ImageView) root.findViewById(R.id.noEventsIcon);
        mNoEventMainView = (TextView) root.findViewById(R.id.noEventsMain);

        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.scanNewBarcode();
            }
        });

        return root;
    }

    @Override
    public void showEvents(Cursor events) {
        mListAdapter.swapCursor(events);

        mListEventView.setVisibility(View.VISIBLE);
        mNoEventsView.setVisibility(View.GONE);
    }

    @Override
    public void showNoEvents() {
        showNoEventsViews(
                getResources().getString(R.string.no_events_all),
                R.drawable.ic_mood_bad_black_24dp
        );
    }


    @Override
    public void showNoEventsWithBookmarkedProduct() {
        showNoEventsViews(
                getResources().getString(R.string.no_events_with_bookmarked_product),
                R.drawable.ic_loyalty_black_24dp
        );
    }

    @Override
    public void showNoEventsViews(String mainText, int iconRes) {
        mListEventView.setVisibility(View.GONE);
        mNoEventsView.setVisibility(View.VISIBLE);

        mNoEventMainView.setText(mainText);
        mNoEventIcon.setImageDrawable(getResources().getDrawable(iconRes));
    }

    @Override
    public void showEventDetailsUi(Event event) {
        // in it's own Activity, since it makes more sense that way and it gives us the flexibility
        // to show some Intent stubbing.
        Intent intent = new Intent(getContext(), ProductActivity.class);
        intent.putExtra(ProductActivity.EXTRA_TASK_ID, event.getBarcode());
        startActivity(intent);
    }

    @Override
    public void showLoadingEventsError() {
        showMessage(getString(R.string.loading_events_error));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    public interface EventItemListener {

        void onEventClick(Event clickedEvent);

        void onErrorEventClick(Event clickedErrorEvent);

    }

    private static class EventsCursorAdapter extends CursorAdapter {

        private final EventItemListener mItemListener;

        public EventsCursorAdapter(Context context, EventItemListener eventItemListener) {
            super(context, null, 0);
            this.mItemListener = eventItemListener;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            ViewHolder viewHolder = (ViewHolder) view.getTag();

            final Event event = Event.from(cursor);
            final Product product = Product.from(cursor);

            String status = event.getStatus();

            if (status == Event.STATUS_OK) {
                viewHolder.title.setText(product.getProductName());
                viewHolder.subtitle.setText(product.getBrands() + " - " + product.getQuantity());
                viewHolder.rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mItemListener.onEventClick(event);
                    }
                });
            } else {

                viewHolder.title.setText("Barcode: " + event.getBarcode());

                switch (status) {
                    case Event.STATUS_NO_NETWORK:
                        viewHolder.subtitle.setText(R.string.event_subtitle_no_network);
                        break;
                    default:
                    case Event.STATUS_NOT_IN_OFF_DATABASE:
                        viewHolder.subtitle.setText(R.string.event_subtitle_not_in_off_database);
                        break;
                    case Event.STATUS_NOT_A_PRODUCT:
                        viewHolder.subtitle.setText(R.string.event_subtitle_not_a_product);
                        break;
                }

                viewHolder.rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mItemListener.onErrorEventClick(event);
                    }
                });
            }
        }

        public static class ViewHolder {
            public final View rowView;
            public final TextView title;
            public final TextView subtitle;

            public ViewHolder(View view) {
                rowView = view;
                title = (TextView) view.findViewById(R.id.title);
                subtitle = (TextView) view.findViewById(R.id.subtitle);
            }
        }
    }
}
