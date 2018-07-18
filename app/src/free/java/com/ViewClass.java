package com;

import android.content.Context;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class ViewClass {
    //This class is used to retrieve the adview in the free version. In the paid version it returns null

    public static View getView(Context mContext){

        AdView adView = new AdView(mContext);
        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId(mContext.getString(com.udacity.gradle.builditbigger.R.string.ad_unit_id));

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);

        return adView;
    }
}
