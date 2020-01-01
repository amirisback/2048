package com.frogobox.basegameboard2048.ui.activity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.frogobox.basegameboard2048.R;
import com.frogobox.basegameboard2048.base.ui.BaseGamesActivity;
import com.frogobox.basegameboard2048.view.adapter.HelpExpandableListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class HelpActivity extends BaseGamesActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        LinkedHashMap<String, List<String>> expandableListDetail = buildData();

        ExpandableListView generalExpandableListView = findViewById(R.id.generalExpandableListView);
        generalExpandableListView.setAdapter(new HelpExpandableListAdapter(this, new ArrayList<>(expandableListDetail.keySet()), expandableListDetail));

        overridePendingTransition(0, 0);
    }

    private LinkedHashMap<String, List<String>> buildData() {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        expandableListDetail.put(getString(R.string.help_whatis), Collections.singletonList(getString(R.string.help_whatis_answer)));
        expandableListDetail.put(getString(R.string.help_privacy), Collections.singletonList(getString(R.string.help_privacy_answer)));
        expandableListDetail.put(getString(R.string.help_permission), Collections.singletonList(getString(R.string.help_permission_answer)));
        expandableListDetail.put(getString(R.string.help_play), Collections.singletonList(getString(R.string.help_play_answer)));
        expandableListDetail.put(getString(R.string.help_play_how), Collections.singletonList(getString(R.string.help_play_how_answer)));
        expandableListDetail.put(getString(R.string.help_play_add), Collections.singletonList(getString(R.string.help_play_add_answer)));
        expandableListDetail.put(getString(R.string.help_tip), Collections.singletonList(getString(R.string.help_tip_answer)));
        expandableListDetail.put(getString(R.string.help_undo), Collections.singletonList(getString(R.string.help_undo_answer)));
        expandableListDetail.put(getString(R.string.help_color), Collections.singletonList(getString(R.string.help_color_answer)));


        return expandableListDetail;
    }

    protected int getNavigationDrawerID() {
        return R.id.nav_help;
    }

}
