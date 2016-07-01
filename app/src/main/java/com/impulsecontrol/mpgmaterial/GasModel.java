package com.impulsecontrol.mpgmaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by droid on 6/26/16.
 */
public class GasModel {

    private List<GasNode> gas_data = new ArrayList<>();

    public void addGasNode(GasNode g) {
        gas_data.add(g);
        Collections.sort(gas_data, new Comparator<GasNode>() {
            @Override
            public int compare(GasNode g1, GasNode g2) {
                return g1.mileage - g2.mileage;
            }
        });
        recalculateMPGs();

     }

    public List<GasNode> getGasNodes() {
        return gas_data;
    }

    public Integer getIndexByMileage(Integer mileage) {
        Integer index = 0;
        for(GasNode g : gas_data) {
            if(mileage == g.mileage) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public Integer getIndex(GasNode node) {
        return getIndexByMileage(node.mileage);
    }


    public void deleteGasNode(GasNode g) {
        gas_data.remove(g);
        recalculateMPGs();
    }


    public Integer getModelSize() {
        return gas_data.size();
    }
    public void deleteGasNodeByMileage(Integer mileage) {
        gas_data.remove(getIndexByMileage(mileage));
        recalculateMPGs();
    }

    public void calculateMPG(ListIterator<GasNode> node, GasNode g) {
        if(node.hasPrevious() ) {
            GasNode top = node.previous();
            if(g.full_tank) {
                if (top.full_tank) {
                    g.mpg = (g.mileage - top.mileage) / (g.gallons);
                } else {
                    g.gallons += top.gallons;
                    calculateMPG(node, g);
                    g.gallons -= top.gallons;
                }
            }
        } 
    }

    public void recalculateMPGs() {
        ListIterator<GasNode> iter = gas_data.listIterator(gas_data.size()-1);
        while(iter != gas_data.listIterator(1) && iter.hasPrevious()) {
            calculateMPG(iter, gas_data.get(iter.nextIndex()));
        }
    }

    public Double getMostRecentMPG() {
        return gas_data.get(gas_data.size() - 1).mpg;
    }

    public List<Double> getMPGs() {
        List<Double> mpgs = new ArrayList<>();
        for(GasNode g : gas_data) {
            mpgs.add(g.mpg);
        }
        return mpgs;
    }



}
