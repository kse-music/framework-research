package cn.hiboot.framework.research.gremlin;

import com.google.common.collect.Lists;
import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.apache.tinkerpop.gremlin.process.traversal.P.neq;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;
import static org.apache.tinkerpop.gremlin.process.traversal.step.util.WithOptions.none;


/**
 * description about this class
 *
 * @author DingHao
 * @since 2020/2/17 21:54
 */
public class GremlinDemo {

    static {
        Configuration conf = new BaseConfiguration();
        conf.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_VERTEX_ID_MANAGER, TinkerGraph.DefaultIdManager.INTEGER.name());
        conf.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_EDGE_ID_MANAGER, TinkerGraph.DefaultIdManager.INTEGER.name());
        conf.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_VERTEX_PROPERTY_ID_MANAGER, TinkerGraph.DefaultIdManager.LONG.name());
//        conf.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_GRAPH_LOCATION, "/work/graph.json");
//        conf.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_GRAPH_FORMAT, "graphson");
//        TinkerGraph graph = TinkerGraph.open(conf);
    }

    @Test
    public void research(){
        TinkerGraph graph = TinkerGraph.open();
        Vertex ly = graph.addVertex(T.id, 1L, T.label, "人", "id",3L,"name", "李延","synonym",Lists.newArrayList("Ly","ly"));
        Vertex hyz = graph.addVertex(T.id, 2L, T.label, "机构", "id",4L,"name", "海乂知","synonym",Lists.newArrayList("PlantData","plantdata"));
        Edge edge = ly.addEdge("任职", hyz, "attr_time_from", new Date());
        GraphTraversalSource g = graph.traversal();
        System.out.println(g.V(1L).out().toList());
        System.out.println(g.V(1L).outE().toList());
        System.out.println(g.E(edge).outV());

        System.out.println(g.V().toList());
        System.out.println(g.V().properties().toList());
        System.out.println(g.E().properties().toList());
    }

    @Test
    public void demo(){
        TinkerGraph graph = TinkerFactory.createModern();

        GraphTraversalSource g = graph.traversal();
        //General Steps
        //map
        System.out.println(g.V(1).out().values("name").toList());
        System.out.println(g.V(1).out().map(it -> it.get().value("name")).toList());
        System.out.println(g.V(1).out().map(values("name")));

        //filter
        System.out.println(g.V().filter(it -> it.get().label().equals("person")).toList());
        System.out.println(g.V().filter(label().is("person")).toList());
        System.out.println(g.V().hasLabel("person").toList());


        //sideEffect
        g.V().hasLabel("person").sideEffect(System.out::println);
        System.out.println(g.V().sideEffect(outE().count().aggregate("o")).sideEffect(inE().count().aggregate("i")).cap("o", "i").toList());

        //branch
        System.out.println(g.V().branch(it -> it.get().value("name")).option("marko", values("age")).option(none, values("name")).toList());
        System.out.println(g.V().branch(values("name")).option("marko", values("age")).option(none, values("name")).toList());
        System.out.println(g.V().choose(has("name", "marko"), values("age"), values("name")).toList());

        //Terminal Steps
        System.out.println(g.V().out("created").hasNext());
        System.out.println(g.V().out("created").next());
        System.out.println(g.V().out("created").next(2));
        System.out.println(g.V().out("nothing").tryNext());
        System.out.println(g.V().out("created").toList());
        System.out.println(g.V().out("created").toSet());
        System.out.println(g.V().out("created").toBulkSet());
        List<Vertex> results = Lists.newArrayList(graph.addVertex("blah",3));
        System.out.println(g.V().out("created").fill(results));
        g.addV("person").iterate();
        
        //AddEdge Step
        System.out.println(g.V(1).as("a").out("created").in("created").where(neq("a")).addE("co-developer").from("a").property("year", 2009).toList());


        graph.close();

    }

}
