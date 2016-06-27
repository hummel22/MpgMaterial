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
        Collections.sort(gas_data, new Comparator<GasNode>() {
            @Override
            public int compare(GasNode g1, GasNode g2) {
                return g1.mileage - g2.mileage;
            }
        });
        calculateMPG(gas_data.listIterator(gas_data.size()), g);
        gas_data.add(g);
     }

    public void deleteGasNode(GasNode g) {
        gas_data.remove(g);
    }

    public void calculateMPG(ListIterator<GasNode> node, GasNode g) {
        if(node.hasPrevious() && g.full_tank) {
            GasNode top = node.previous();
            if (top.full_tank) {
                g.mpg = (g.mileage - top.mileage) / (g.gallons);
            } else {
                    g.gallons += top.gallons;
                    calculateMPG(node, g);
                    g.gallons -= top.gallons;
            }
        }
    }

    public Double getMPG() {
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
