import com.impulsecontrol.mpgmaterial.GasModel;
import com.impulsecontrol.mpgmaterial.GasNode;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by droid on 6/26/16.
 */
public class GasModelUnitTest {



    @Test
    public void firstNode() throws Exception {

        GasNode node0 = new GasNode();
        GasModel model = new GasModel();
        node0.mileage = 100;
        node0.gallons = 10.0;
        node0.mpg = null;
        node0.full_tank = true;
        model.addGasNode(node0);
        assertEquals(null, model.getMostRecentMPG());
    }

    @Test
    public void SecondtNode() throws Exception {
        GasModel model = new GasModel();

        GasNode node0 = new GasNode();
        node0.mileage = 100;
        node0.gallons = 10.0;
        node0.mpg = null;
        node0.full_tank = true;

        GasNode node1 = new GasNode();
        node1.mileage = 250;
        node1.gallons = 10.0;
        node1.mpg = null;
        node1.full_tank = true;

        model.addGasNode(node0);
        model.addGasNode(node1);
        assertEquals((Double)15.0, model.getMostRecentMPG());
    }

    @Test
    public void thirdsNode() throws Exception {
        GasModel model = new GasModel();

        GasNode node0 = new GasNode();
        node0.mileage = 100;
        node0.gallons = 10.0;
        node0.mpg = null;
        node0.full_tank = true;

        GasNode node1 = new GasNode();
        node1.mileage = 250;
        node1.gallons = 10.0;
        node1.mpg = null;
        node1.full_tank = true;

        GasNode node2 = new GasNode();
        node2.mileage = 500;
        node2.gallons = 10.0;
        node2.mpg = null;
        node2.full_tank = true;

        model.addGasNode(node0);
        model.addGasNode(node1);
        model.addGasNode(node2);
        assertEquals((Double)25.0, model.getMostRecentMPG());
        assertEquals(null, node0.mpg);
        assertEquals((Double)15.0, node1.mpg);
        assertEquals((Double)25.0, node2.mpg);
        List<Double> mpgs = model. getMPGs();
        assertEquals(null, mpgs.get(0));
        assertEquals((Double)15.0, mpgs.get(1));
        assertEquals((Double)25.0, mpgs.get(2));

    }
//
    @Test
    public void NotFull() throws Exception {
        GasModel model = new GasModel();

        GasNode node0 = new GasNode();
        node0.mileage = 100;
        node0.gallons = 10.0;
        node0.mpg = null;
        node0.full_tank = true;

        GasNode node1 = new GasNode();
        node1.mileage = 250;
        node1.gallons = 10.0;
        node1.mpg = null;
        node1.full_tank = false;

        GasNode node2 = new GasNode();
        node2.mileage = 500;
        node2.gallons = 10.0;
        node2.mpg = null;
        node2.full_tank = true;

        model.addGasNode(node0);
        model.addGasNode(node1);
        model.addGasNode(node2);
        assertEquals((Double)20.0, model.getMostRecentMPG());
        assertEquals(null, node0.mpg);
        assertEquals(null, node1.mpg);
        assertEquals((Double)20.0, node2.mpg);
        List<Double> mpgs = model. getMPGs();
        assertEquals(null, mpgs.get(0));
        assertEquals(null, mpgs.get(1));
        assertEquals((Double)20.0, mpgs.get(2));
    }


    @Test
    public void notFullTwoSpace() throws Exception {
        GasModel model = new GasModel();

        GasNode node0 = new GasNode();
        node0.mileage = 100;
        node0.gallons = 10.0;
        node0.mpg = null;
        node0.full_tank = true;

        GasNode node1 = new GasNode();
        node1.mileage = 250;
        node1.gallons = 10.0;
        node1.mpg = null;
        node1.full_tank = false;

        GasNode node2 = new GasNode();
        node2.mileage = 500;
        node2.gallons = 10.0;
        node2.mpg = null;
        node2.full_tank = false;

        GasNode node3 = new GasNode();
        node3.mileage = 1000;
        node3.gallons = 10.0;
        node3.mpg = null;
        node3.full_tank = true;

        model.addGasNode(node0);
        model.addGasNode(node1);
        model.addGasNode(node2);
        model.addGasNode(node3);

        assertEquals((Double)30.0, model.getMostRecentMPG());
        assertEquals(null, node0.mpg);
        assertEquals(null, node1.mpg);
        assertEquals(null, node2.mpg);
        assertEquals((Double)30.0, node3.mpg);

        List<Double> mpgs = model. getMPGs();
        assertEquals(null, mpgs.get(0));
        assertEquals(null, mpgs.get(1));
        assertEquals(null, mpgs.get(2));
        assertEquals((Double)30.0, mpgs.get(3));
    }


    @Test
    public void incorrectOrder() throws Exception {
        GasModel model = new GasModel();

        GasNode node0 = new GasNode();
        node0.mileage = 100;
        node0.gallons = 10.0;
        node0.mpg = null;
        node0.full_tank = true;

        GasNode node1 = new GasNode();
        node1.mileage = 250;
        node1.gallons = 10.0;
        node1.mpg = null;
        node1.full_tank = false;

        GasNode node2 = new GasNode();
        node2.mileage = 500;
        node2.gallons = 10.0;
        node2.mpg = null;
        node2.full_tank = true;

        model.addGasNode(node1);
        model.addGasNode(node0);
        model.addGasNode(node2);
        assertEquals((Double)20.0, model.getMostRecentMPG());
        assertEquals(null, node0.mpg);
        assertEquals(null, node1.mpg);
        assertEquals((Double)20.0, node2.mpg);
        List<Double> mpgs = model. getMPGs();
        assertEquals(null, mpgs.get(0));
        assertEquals(null, mpgs.get(1));
        assertEquals((Double)20.0, mpgs.get(2));
    }



    @Test
    public void incorrectOrderAllFull() throws Exception {
        GasModel model = new GasModel();

        GasNode node0 = new GasNode();
        node0.mileage = 100;
        node0.gallons = 10.0;
        node0.mpg = null;
        node0.full_tank = true;

        GasNode node1 = new GasNode();
        node1.mileage = 250;
        node1.gallons = 10.0;
        node1.mpg = null;
        node1.full_tank = true;

        GasNode node2 = new GasNode();
        node2.mileage = 500;
        node2.gallons = 10.0;
        node2.mpg = null;
        node2.full_tank = true;

        model.addGasNode(node1);
        model.addGasNode(node0);
        model.addGasNode(node2);

        assertEquals((Double)25.0, model.getMostRecentMPG());
        assertEquals(null, node0.mpg);
        assertEquals((Double)15.0, node1.mpg);
        assertEquals((Double)25.0, node2.mpg);
        List<Double> mpgs = model. getMPGs();
        assertEquals(null, mpgs.get(0));
        assertEquals((Double)15.0, mpgs.get(1));
        assertEquals((Double)25.0, mpgs.get(2));
    }




    @Test
    public void RecalculateMPGS() throws Exception {
        GasModel model = new GasModel();

        GasNode node0 = new GasNode();
        node0.mileage = 100;
        node0.gallons = 10.0;
        node0.mpg = null;
        node0.full_tank = true;

        GasNode node1 = new GasNode();
        node1.mileage = 250;
        node1.gallons = 10.0;
        node1.mpg = null;
        node1.full_tank = true;

        GasNode node2 = new GasNode();
        node2.mileage = 500;
        node2.gallons = 10.0;
        node2.mpg = null;
        node2.full_tank = true;

        model.addGasNode(node0);
        model.addGasNode(node1);
        model.addGasNode(node2);
        assertEquals((Double)25.0, model.getMostRecentMPG());
        assertEquals(null, node0.mpg);
        assertEquals((Double)15.0, node1.mpg);
        assertEquals((Double)25.0, node2.mpg);
        List<Double> mpgs = model. getMPGs();
        assertEquals(null, mpgs.get(0));
        assertEquals((Double)15.0, mpgs.get(1));
        assertEquals((Double)25.0, mpgs.get(2));


        node2.mileage = 1000;
        node1.mileage = 500;


        model.recalculateMPGs();

        assertEquals((Double)50.0, model.getMostRecentMPG());
        assertEquals(null, node0.mpg);
        assertEquals((Double)40.0, node1.mpg);
        assertEquals((Double)50.0, node2.mpg);
        mpgs = model. getMPGs();
        assertEquals(null, mpgs.get(0));
        assertEquals((Double)40.0, mpgs.get(1));
        assertEquals((Double)50.0, mpgs.get(2));
    }

    @Test
    public void deleteGasNode() throws Exception {
        GasModel model = new GasModel();

        GasNode node0 = new GasNode();
        node0.mileage = 100;
        node0.gallons = 10.0;
        node0.mpg = null;
        node0.full_tank = true;

        GasNode node1 = new GasNode();
        node1.mileage = 250;
        node1.gallons = 10.0;
        node1.mpg = null;
        node1.full_tank = true;

        GasNode node2 = new GasNode();
        node2.mileage = 500;
        node2.gallons = 10.0;
        node2.mpg = null;
        node2.full_tank = true;

        model.addGasNode(node1);
        model.addGasNode(node0);
        model.addGasNode(node2);

        assertEquals((Double)25.0, model.getMostRecentMPG());
        assertEquals(null, node0.mpg);
        assertEquals((Double)15.0, node1.mpg);
        assertEquals((Double)25.0, node2.mpg);
        List<Double> mpgs = model. getMPGs();
        assertEquals(null, mpgs.get(0));
        assertEquals((Double)15.0, mpgs.get(1));
        assertEquals((Double)25.0, mpgs.get(2));

        model.deleteGasNode(node1);
        assertEquals((Double)40.0, model.getMostRecentMPG());
        assertEquals(null, node0.mpg);
        assertEquals((Double)40.0, node2.mpg);
    }

}
