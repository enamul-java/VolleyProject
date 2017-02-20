package era.bd.volleyproject;

import android.preference.PreferenceActivity;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ehaque on 2/16/17.
 * http://loopj.com/android-async-http/
 */

public class TwitterRestClientUsage {
    public void getPublicTimeline() throws JSONException {
        TwitterRestClient.get("statuses/public_timeline.json", null, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
            }


            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                JSONObject firstEvent = null;
                try {
                    firstEvent = (JSONObject) timeline.get(0);
                    String tweetText = firstEvent.getString("text");
                    // Do something with the response
                    System.out.println(tweetText);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
    }
}
