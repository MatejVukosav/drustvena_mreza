package com.example.vuki.drustvena_mreza_faks.helpers;

/**
 * Created by Vuki on 2.1.2016..
 */
public class BundleKeys  {


    public static String PACKAGE="com.example.vuki.drustvena_mreza_faks";
    public static String COMMENT=PACKAGE+"comment";
    public static String FRIEND_USER_ID =PACKAGE+"user_id";
    public static String USER_ABOUT=PACKAGE+"user_about";





/*    private void showSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
        if (tabLayout.getTabCount() <= TAB_LIMIT - 1) {
            snackbar = Snackbar.make(createTabButton,
                    String.format(getString(R.string.tab_count), tabLayout.getTabCount()), Snackbar.LENGTH_LONG)
                    .setAction(R.string.dismiss, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });

        } else {
            snackbar = Snackbar.make(tabLayout, R.string.tab_limit, Snackbar.LENGTH_LONG);
        }
        FontHelper.applyStyleToSnackbar(snackbar);
        snackbar.show();

    }

    private void restartActivity(boolean useCustomFonts) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(USE_CUSTOM_FONTS, useCustomFonts);
        finish();
        startActivity(intent);
    }*/
}
