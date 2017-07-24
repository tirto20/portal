package id.co.sigma.portalsigma.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import id.co.sigma.portalsigma.fragment.CorporateNewsFragment;
import id.co.sigma.portalsigma.fragment.MindFragment;
import id.co.sigma.portalsigma.fragment.PostNowFragment;

/**
 * Created by Aries Satriana on 16/09/2016.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    private String[] titles=new String[]{"News","Socials","Post-It","Profile"};
    private CorporateNewsFragment corporateFragment;
    private MindFragment mindFragment;
    private PostNowFragment postNowFragment;

    public HomePagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                if(corporateFragment ==null){
                    corporateFragment=new CorporateNewsFragment();
                }
                return corporateFragment;
            case 1 :
                if(mindFragment == null){
                    mindFragment=new MindFragment();
                }
                return mindFragment;
            case 2:
                if(postNowFragment == null){
                    postNowFragment=new PostNowFragment();
                }
                return postNowFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
