package cn.hiboot.framework.research.guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import com.google.common.collect.Table.Cell;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.hash.BloomFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;

@Slf4j
public class GuavaDemo {

    // 预计元素个数
    private long size = 1024 * 1024;

    private BloomFilter<String> bloom = BloomFilter.create((from, into) -> into.putString(from, Charset.forName("UTF-8")), size);

    @Test
    public void bloomFilter() {
        bloom.put("布隆过滤器");
        log.info("{}",bloom.mightContain("布隆过滤器"));
    }

    @Test
    public void multiSet() {
        String[] words = {"a", "b", "c", "a"};
        Map<String, Integer> counts = Maps.newHashMap();
        Multiset<String> multiSet = HashMultiset.create();
        Multimap<String, Integer> map1 = HashMultimap.create();//不存相同key和value都相同的
        Multimap<String, Integer> map2 = ArrayListMultimap.create();
        for (String word : words) {
            Integer count = counts.get(word);
            if (count == null) {
                count = 1;
                counts.put(word, 1);
            } else {
                counts.put(word, count + 1);
            }
            multiSet.add(word);
            map1.put(word, count);
            map2.put(word, count);
        }
    }

    @Test
    public void testListsTransform() {
        List<PersonDb> personDbs = Lists.newArrayList(new PersonDb("zhangsan", 20), new PersonDb("lisi", 24), new PersonDb("wangwu", 30));

        //返回的列表是原有列表的一个转换视图，对原有集合的修改当然会反映到新集合中,transform是单向的，无法向结果列表中添加新元素
        List<PersonVo> personVos = Lists.transform(personDbs, (personDb) -> personDbToVo(personDb));

        for (PersonDb personDb : personDbs) {
            personDb.setMsg("hello world!");
        }
        //Collections.shuffle(personVos);
        //personVos = ImmutableList.copyOf(personVos);
        //        personVos = Lists.newArrayList(personVos);
        for (PersonVo personVo : personVos) {
            personVo.setMsg("Merry Christmas!");
        }
        //        personVos.add(personDbToVo(new PersonDb("sting", 30)));
        System.out.println(personVos);
    }

    private PersonVo personDbToVo(PersonDb personDb) {
        Preconditions.checkNotNull(personDb, "[PersonDbToVo]personDb为null");
        PersonVo personVo = new PersonVo();
        personVo.setName(personDb.getName() + ",from Db");
        personVo.setAge(personDb.getAge());
        personVo.setMsg(personDb.getMsg());
        return personVo;
    }

    @Test
    public void eventBus() {
        final AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(10));

        eventBus.register(new Object() {

            @Subscribe
            @AllowConcurrentEvents
            public void lister(Integer integer) {
//				System.out.printf("%s from int%n", integer);
                System.out.println(Thread.currentThread().getName() + "=" + integer);
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "=" + integer);
                }
            }

            @Subscribe
            public void lister(Number integer) {
//				System.out.printf("%s from Number%n", integer);
            }

            @Subscribe
            public void lister(Long integer) {
                System.out.printf("%s from long%n", integer);
            }
        });
        eventBus.post(1);
        eventBus.post(2);
//		eventBus.post(1L);
    }

    @Test
    public void table() {
        Table<String, String, Integer> tables = HashBasedTable.create();
        tables.put("a", "javase", 80);
        tables.put("b", "javaee", 90);
        tables.put("c", "javame", 100);
        tables.put("d", "guava", 70);
        tables.put("e", "", 60);

        //得到所有行数据 tables.cellSet()
        Set<Cell<String, String, Integer>> cells = tables.cellSet();
        for (Cell<String, String, Integer> temp : cells) {
            System.out.println(temp.getRowKey() + " " + temp.getColumnKey() + " " + temp.getValue());
        }

        Set<String> students = tables.rowKeySet();
        for (String str : students) {
            System.out.print(str + "\t");
        }

        Set<String> courses = tables.columnKeySet();
        for (String str : courses) {
            System.out.print(str + "\t");
        }

        Collection<Integer> scores = tables.values();
        for (Integer in : scores) {
            System.out.print(in + "\t");
        }

        for (String str : students) {
            Map<String, Integer> rowMap = tables.row(str);
            Set<Entry<String, Integer>> setEntry = rowMap.entrySet();
            for (Entry<String, Integer> entry : setEntry) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }

        for (String str : courses) {
            Map<String, Integer> rowMap2 = tables.column(str);
            Set<Entry<String, Integer>> setEntry2 = rowMap2.entrySet();
            for (Entry<String, Integer> entry : setEntry2) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
    }

    static class PersonDb {
        private String name;
        private int age;
        private String msg;
        public PersonDb(String name, int age){
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("name", name)
                    .add("age", age)
                    .add("msg", msg).toString();
        }
    }

    static class PersonVo {
        private String name;
        private int age;
        private String msg;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("name", name)
                    .add("age", age)
                    .add("msg", msg).toString();
        }
    }

}  

