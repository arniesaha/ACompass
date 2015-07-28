package com.audiocompass.assignment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import com.audiocompass.assignment.utils.PhotoUtils;
import com.audiocompass.assignment.R;

public class MainActivity extends BaseActivity implements ActionBar.TabListener{

	public ActionBar mActionBar;
	
	public static final int NUM_TABS =2;
	public static final int RECENT_POSITION = 0;
	public static final int CATEGORY_POSITION = 1;
	
	public static final String RECENT = "Photos";
	public static final String CATEGORY = "Music";
	
	public static final String TAGNAME_IMAGE_GRID = "Image Grid";
	private Fragment[] mFragments = new Fragment[NUM_TABS];
	private Fragment mCategoryGridFragment = null;
	
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Set up Fragment Holder
		setContentView(R.layout.activity_main);
		mActionBar = getSupportActionBar();
		//Set Actionbar as Tab navigation
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Add Tabs
		mActionBar.addTab(mActionBar.newTab().setText(RECENT).setTabListener(this));
		//mActionBar.addTab(mActionBar.newTab().setText(POPULAR).setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText(CATEGORY).setTabListener(this));
		

	} 
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		int position = tab.getPosition();
		Fragment af = null;
		switch(position){
		case RECENT_POSITION:
			if(mFragments[position] == null){
				mFragments[position] = new ImageGridFragment();
				Bundle args = new Bundle();
				args.putString(ImageGridFragment.URL_KEY, PhotoUtils.AC_URL);
				mFragments[position].setArguments(args);
				ft.add(R.id.fragment_container, mFragments[position], RECENT);
			}
			af = mFragments[position];
			break;
		
			
		case CATEGORY_POSITION:
			if(mFragments[position] == null){
				mFragments[position] = new MusicFragment();
				ft.add(R.id.fragment_container, mFragments[position], CATEGORY);
				af = mFragments[position];
			}
			else if(mCategoryGridFragment != null)
				af = mCategoryGridFragment;
			else af = mFragments[position];
			break;
		
			
			default:
				return;
		
		}
		ft.attach(af);
		
	}


	@Override
	public void onBackPressed() {
		// if current tab is category tab and it is showing image on ImageGridFragment then back to CategoryListFragment
		Tab tab = getSupportActionBar().getSelectedTab();
		if(tab.getPosition() == CATEGORY_POSITION && mCategoryGridFragment != null){
			FragmentManager fm = getSupportFragmentManager();
			fm.beginTransaction().detach(mCategoryGridFragment)
			.attach(mFragments[CATEGORY_POSITION])
			.commit();
			mCategoryGridFragment = null;
			return;
		}
		
		
		else
	    {
	          int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
	    if(backStackEntryCount == 0){
	         new AlertDialog.Builder(this)
	           .setMessage(R.string.exitmessage)
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                    MainActivity.this.finish();
	               }
	           })
	           .setNegativeButton("No", null)
	           .show();;   // write your code to switch between fragments.
	    }
	    else{
	        super.onBackPressed();
	    }


	    }
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		//Remove Fragment from fragment holder
		if(tab.getPosition() == CATEGORY_POSITION && mCategoryGridFragment != null)
			ft.detach(mCategoryGridFragment);
		
				
		ft.detach(mFragments[tab.getPosition()]);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Hide or show search view on Search tabl
		
		
	}

	

	
}

