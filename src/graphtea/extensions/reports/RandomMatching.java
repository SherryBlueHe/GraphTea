// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports;


import graphtea.graph.graph.Edge;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.*;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "maxium_matching", abbreviation = "_max_match")
public class RandomMatching implements GraphReportExtension {
    public String getName() {
        return "Random Matching";
    }

    public String getDescription() {
        return "Random Matching";
    }

    Random r = new Random();
    Random r2 = new Random(10);
    public Object calculate(GraphData gd) {
        SubGraph sg = new SubGraph();
        int limit=r2.nextInt(gd.getGraph().getEdgesCount());

        Vector<Integer> vi = new Vector<Integer>();
        HashMap<Vertex,Vertex> vv= new HashMap<Vertex, Vertex>();
        for(int i=0;i<gd.getGraph().getVerticesCount();i++) {
            vi.add(i);
        }

        Vertex[] varr = gd.getGraph().getVertexArray();
        Vertex[] rvarr = (Vertex[]) rotate(varr,r.nextInt(gd.getGraph().getVerticesCount()-2));


        for(Vertex v1 : rvarr) {
            if(vv.size() > limit) break;
            for(Vertex v2 : gd.getGraph().getNeighbors(v1)) {
                if(vi.contains(v1.getId()) && vi.contains(v2.getId())) {
                    vv.put(v1,v2);

                    vi.remove(vi.indexOf(v1.getId()));
                    vi.remove(vi.indexOf(v2.getId()));
                    break;
                }
            }
        }

        for(Vertex v : vv.keySet()) {
            sg.vertices.add(v);
            sg.vertices.add(vv.get(v));
        }

        for(Vertex v : vv.keySet()) {
            sg.edges.add(gd.getGraph().getEdge(v,vv.get(v)));
        }

        Vector ret = new Vector();
        ret.add("Number of Matching:" + sg.edges.size());
        ret.add(sg);

        return ret;
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Property";
	}

    Vertex[] rotate(final Vertex[] unOrderedArr, final int orderToRotate) {
        final int length = unOrderedArr.length;
        final Vertex[] rotated = new Vertex[length];
        for (int i = 0; i < length; i++) {
            rotated[(i + orderToRotate) % length] = unOrderedArr[i];
        }
        return rotated;
    }
}
