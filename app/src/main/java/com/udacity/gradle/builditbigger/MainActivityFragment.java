package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ViewClass;



/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    //copied from https://medium.com/@thiagolopessilva/the-handling-multiple-java-source-and-resources-using-flavors-on-gradle-18a4b581285b
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        if (!(BuildConfig.PAID_VERSION)) {

            View adView = ViewClass.getView(getActivity());
            View view = root.findViewById(R.id.dummyView);
            ViewGroup parent = (ViewGroup) view.getParent();

            RelativeLayout.LayoutParams adParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            adParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            parent.addView(adView,adParams);

        }
        return root;
    }
}
