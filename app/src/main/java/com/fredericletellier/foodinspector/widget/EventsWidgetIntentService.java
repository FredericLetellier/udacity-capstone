package com.fredericletellier.foodinspector.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Binder;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.fredericletellier.foodinspector.R;
import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.source.local.db.EventPersistenceContract;
import com.fredericletellier.foodinspector.product.ProductActivity;

/**
 * Created by fletellier on 09/04/17.
 */

public class EventsWidgetIntentService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();

                data = getContentResolver().query(
                        EventPersistenceContract.EventEntry.buildEventUri(),
                        EventPersistenceContract.EventEntry.EVENT_COLUMNS,
                        null,
                        null,
                        null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }

                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_events_item);

                int event_id = data.getInt(data.getColumnIndex(EventPersistenceContract.EventEntry._ID));
                String timsetamp = data.getString(data.getColumnIndex(EventPersistenceContract.EventEntry.COLUMN_NAME_TIMESTAMP));
                String barcode = data.getString(data.getColumnIndex(EventPersistenceContract.EventEntry.COLUMN_NAME_BARCODE));
                String status = data.getString(data.getColumnIndex(EventPersistenceContract.EventEntry.COLUMN_NAME_STATUS));

                views.setTextViewText(R.id.widget_item_title, barcode);

                switch (status) {
                    case Event.STATUS_OK:
                        views.setImageViewResource(R.id.widget_item_icon, R.drawable.ic_search_black_24dp);
                        views.setTextViewText(R.id.widget_item_subtitle, getResources().getString(R.string.event_subtitle_available));
                        final Intent fillInIntent = new Intent();
                        fillInIntent.putExtra(ProductActivity.ARGUMENT_PRODUCT_BARCODE, barcode);
                        views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
                        break;
                    case Event.STATUS_NO_NETWORK:
                        views.setImageViewResource(R.id.widget_item_icon, R.drawable.ic_cloud_off_black_24dp);
                        views.setTextViewText(R.id.widget_item_subtitle, getResources().getString(R.string.event_subtitle_no_network));
                        break;
                    default:
                    case Event.STATUS_NOT_IN_OFF_DATABASE:
                        views.setImageViewResource(R.id.widget_item_icon, R.drawable.ic_layers_clear_black_24dp);
                        views.setTextViewText(R.id.widget_item_subtitle, getResources().getString(R.string.event_subtitle_not_in_off_database));
                        break;
                    case Event.STATUS_NOT_A_PRODUCT:
                        views.setImageViewResource(R.id.widget_item_icon, R.drawable.ic_layers_clear_black_24dp);
                        views.setTextViewText(R.id.widget_item_subtitle, getResources().getString(R.string.event_subtitle_not_a_product));
                        break;
                }


                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_events_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position)){
                    return data.getLong(data.getColumnIndex(EventPersistenceContract.EventEntry._ID));
                } else {
                    return position;
                }
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}