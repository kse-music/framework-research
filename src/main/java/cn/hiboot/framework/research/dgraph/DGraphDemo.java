package cn.hiboot.framework.research.dgraph;

//import com.google.common.collect.Lists;
//import com.google.protobuf.ByteString;
//import io.dgraph.DgraphClient;
//import io.dgraph.DgraphGrpc;
//import io.dgraph.DgraphProto;
//import io.dgraph.Transaction;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/8/16 17:15
 */
public class DGraphDemo {

//    private DgraphClient dgraphClient;
    private Gson gson;


    @BeforeEach
    public void init(){
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("192.168.1.64", 9080).usePlaintext().build();
//        DgraphGrpc.DgraphStub stub = DgraphGrpc.newStub(channel);
//        dgraphClient = new DgraphClient(stub);
//        gson = new Gson();
    }

    @Test
    public void query(){

        // Query
//        String query ="{\n" +
//                "  me(func:allofterms(name, \"Star Wars\")) @filter(ge(release_date, \"1970\")) {\n" +
//                "    name\n" +
//                "    release_date\n" +
//                "    revenue\n" +
//                "    running_time\n" +
//                "    director {\n" +
//                "     name\n" +
//                "    }\n" +
//                "    starring {\n" +
//                "     name\n" +
//                "    }\n" +
//                "  }\n" +
//                "}";
//
//        DgraphProto.Response res = dgraphClient.newReadOnlyTransaction().query(query);
//
//        Resp ppl = gson.fromJson(res.getJson().toStringUtf8(), Resp.class);
//
//        System.out.println(ppl);


    }

    @Test
    public void tx(){
//        Transaction txn = dgraphClient.newTransaction();
//        try {
//            Movie movie = new Movie();
//            movie.name = "战狼2";
//            User user = new User();
//            user.name = "吴京";
//            movie.director = Lists.newArrayList(user);
//            User user2 = new User();
//            user2.name = "吴刚";
//            movie.starring = Lists.newArrayList(user);
//            movie.revenue = 5681000000.0;
//            movie.runningTime = 123;
//            movie.releaseDate = "2017-07-27";
//            String json = gson.toJson(movie);
//            DgraphProto.Mutation mu = DgraphProto.Mutation.newBuilder()
//                    .setSetJson(ByteString.copyFromUtf8(json))
//                    .build();
//            txn.mutate(mu);
//            txn.commit();
//        } finally {
//            txn.discard();
//        }
    }

    private static class Resp{
        List<Movie> me;
    }

    private static class User{
        String name;
    }

    private static class Movie{
        String name;
        @SerializedName(value = "release_date")
        String releaseDate;
        Double revenue;
        @SerializedName("running_time")
        Integer runningTime;
        List<User> starring;
        List<User> director;
    }

}
