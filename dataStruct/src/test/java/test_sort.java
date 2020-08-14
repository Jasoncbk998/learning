import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * @Classname test_sort
 * @Description TODO
 * @Date 2020/8/6 2:06 下午
 * @Created by jason
 */

public class test_sort{
    public static class Person implements Comparable<Person> {
        private Integer age;

        public Person(Integer age) {
            this.age = age;
        }

        public Integer getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    '}';
        }

        @Override
        public int compareTo(Person o) {
            if (this.age > o.getAge()) {
                return 1;
            }
            if (this.age < o.getAge()) {
                return -1;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        HashMap<Person, String> treeMap = new HashMap<>();
        treeMap.put(new Person(3), "person1");
        treeMap.put(new Person(18), "person2");
        treeMap.put(new Person(35), "person3");
        treeMap.put(new Person(16), "person4");

        for (Person person : treeMap.keySet()) {
            System.out.println(person);
        }
    }


}




