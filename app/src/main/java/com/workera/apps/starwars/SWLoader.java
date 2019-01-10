package com.workera.apps.starwars;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;


public class SWLoader extends AsyncTaskLoader<List<SWCharacter>> {

    /** Query SWAPI URL */
    private String mUrl;

    /**
     * Constructs a new {@link SWLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public SWLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * Networking tasks being performed @ background thread.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    public List<SWCharacter> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of SWCharacters.
        List<SWCharacter> swCharacters = QueryUtils.fetchSWCharacterData(mUrl);
        return swCharacters;
    }

}
