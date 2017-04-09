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
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fredericletellier.foodinspector.R;
import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.product.ProductActivity;
import com.fredericletellier.foodinspector.scan.BarcodeCaptureActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import static com.google.common.base.Preconditions.checkNotNull;

public class EventsFragment extends Fragment implements EventsContract.View {

    private static final String TAG = EventsFragment.class.getName();

    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final int RC_BARCODE_CAPTURE = 9001;

    private EventsContract.Presenter mPresenter;

    private EventsCursorAdapter mListAdapter;
    private View mNoEventsView;
    private ImageView mNoEventIcon;
    private TextView mNoEventMainView;
    private ListView mListEventView;

    protected View mProgressView;

    /**
     * Listener for clicks on events in the ListView.
     */
    EventItemListener mItemListener = new EventItemListener() {
        @Override
        public void onEventClick(Event clickedEvent) {
            Log.d(TAG, "onEventClick");
            mPresenter.openEventDetails(clickedEvent.getBarcode());
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
        Log.d(TAG, "newInstance");
        return new EventsFragment();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull EventsContract.Presenter presenter) {
        Log.d(TAG, "setPresenter");
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View root = inflater.inflate(R.layout.events_frag, container, false);

        // Set up events view
        mListAdapter = new EventsCursorAdapter(getActivity(), mItemListener);
        mListEventView = (ListView) root.findViewById(R.id.events_list);
        mListEventView.setAdapter(mListAdapter);

        // Set up  no tasks view
        mNoEventsView = root.findViewById(R.id.noEvents);
        mNoEventIcon = (ImageView) root.findViewById(R.id.noEventsIcon);
        mNoEventMainView = (TextView) root.findViewById(R.id.noEventsMain);

        mProgressView = root.findViewById(android.R.id.progress);

        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clickOnFab();
            }
        });

        return root;
    }

    @Override
    public void showEvents(Cursor events) {
        Log.d(TAG, "showEvents");
        mListEventView.setVisibility(View.VISIBLE);
        mNoEventsView.setVisibility(View.INVISIBLE);
        mListAdapter.swapCursor(events);
    }

    @Override
    public void showNoEvents() {
        Log.d(TAG, "showNoEvents");

        showNoEventsViews(
                getResources().getString(R.string.no_events_all),
                R.drawable.ic_mood_bad_black_24dp
        );
    }

    @Override
    public void setLoadingIndicator(final Boolean active) {
        Log.d(TAG, "setLoadingIndicator");
        if (active){
            //show loading indicator
            mProgressView.startAnimation(AnimationUtils.loadAnimation(this.getContext(), android.R.anim.fade_in));
            mProgressView.setVisibility(View.VISIBLE);

        } else {
            // hide loading indicator
            mProgressView.startAnimation(AnimationUtils.loadAnimation(this.getContext(), android.R.anim.fade_out));
            mProgressView.setVisibility(View.GONE);
        }
    }


    @Override
    public void showScanUi() {
        Log.d(TAG, "showScanUi");
        Intent intent = new Intent(this.getContext(), BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false);

        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                    mPresenter.newEvent(barcode);
                } else {
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                Log.d(TAG, "onActivityResult is not a success");
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showNoEventsViews(String mainText, int iconRes) {
        Log.d(TAG, "showNoEventsViews");
        mListEventView.setVisibility(View.INVISIBLE);
        mNoEventsView.setVisibility(View.VISIBLE);

        mNoEventMainView.setText(mainText);
        mNoEventIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), iconRes));
    }

    @Override
    public void showEventDetailsUi(String barcode) {
        Log.d(TAG, "showEventDetailsUi " + barcode);
        Intent intent = new Intent(getContext(), ProductActivity.class);
        intent.putExtra(ProductActivity.ARGUMENT_PRODUCT_BARCODE, barcode);
        startActivity(intent);
    }

    @Override
    public void showLoadingEventsError() {
        Log.d(TAG, "showLoadingEventsError");
        showMessage(getString(R.string.loading_events_error));
    }

    @Override
    public void showNewEventError() {
        Log.d(TAG, "showLoadingEventsError");
        showMessage(getString(R.string.new_event_error));
    }

    private void showMessage(String message) {
        Log.d(TAG, "showMessage");
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
            Log.d(TAG, "EventsCursorAdapter - newView");
            View view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            Log.d(TAG, "EventsCursorAdapter - bindView");

            ViewHolder viewHolder = (ViewHolder) view.getTag();

            final Event event = Event.from(cursor);
            Log.d(TAG, event.toString());


            String status = event.getStatus();

            if (status.equals(Event.STATUS_OK)) {
                viewHolder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_search_black_24dp));
                viewHolder.title.setText(event.getBarcode());
                viewHolder.subtitle.setText(R.string.event_subtitle_available);
                viewHolder.rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mItemListener.onEventClick(event);
                    }
                });
            } else {

                Resources res = context.getResources();
                String barcode = res.getString(R.string.barcode, event.getBarcode());
                viewHolder.title.setText(barcode);

                switch (status) {
                    case Event.STATUS_NO_NETWORK:
                        viewHolder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_cloud_off_black_24dp));
                        viewHolder.subtitle.setText(R.string.event_subtitle_no_network);
                        break;
                    default:
                    case Event.STATUS_NOT_IN_OFF_DATABASE:
                        viewHolder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_layers_clear_black_24dp));
                        viewHolder.subtitle.setText(R.string.event_subtitle_not_in_off_database);
                        break;
                    case Event.STATUS_NOT_A_PRODUCT:
                        viewHolder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_layers_clear_black_24dp));
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
            public final ImageView icon;
            public final TextView title;
            public final TextView subtitle;

            public ViewHolder(View view) {
                rowView = view;
                icon = (ImageView) view.findViewById(R.id.icon);
                title = (TextView) view.findViewById(R.id.title);
                subtitle = (TextView) view.findViewById(R.id.subtitle);
            }
        }
    }
}
