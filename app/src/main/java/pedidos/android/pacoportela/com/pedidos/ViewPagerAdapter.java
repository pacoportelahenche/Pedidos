package pedidos.android.pacoportela.com.pedidos;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Creada por Francisco Portela Henche el 10/02/2020.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentListTitulos = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int posicion) {
        return fragmentList.get(posicion);
    }

    @Override
    public int getCount() {
        return fragmentListTitulos.size();
    }

    @Override
    public CharSequence getPageTitle(int posicion) {
        return fragmentListTitulos.get(posicion);
    }

    public void addFragment(Fragment fragment, String titulo){
        fragmentList.add(fragment);
        fragmentListTitulos.add(titulo);
    }
}
