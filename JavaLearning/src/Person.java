


public class Person{

    private static int nextId = 0; // use static for counter vairbles static makes it
    // so the int nextId will not be assigned to every person
    // but just to the class.
    // It is a varible of the class and not an instance of the class

    private static int generateId(){
        return nextId++; // nextId = nextId + 1
        //return age++;
        // cant do because static doesnt allow non static varibles or functions
    }

    private int id;
    private String name;
    private int age;
    private YearInSchool year;


    public Person(String name, int age, YearInSchool year){ // constructor
        setId(generateId());
        setName(name);
        setAge(age);
        setYear(year);

    }

    public Person (String name, int age){
        this(name,age, YearInSchool.FRESHMAN);
    } // additional constructor for freshman


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public YearInSchool getYear() {
        return year;
    }

    public void setYear(YearInSchool year) {
        this.year = year;
    }


    @Override
    public String toString() {

        return String.format("id : %d name: %s, age: %d, year: %s", id, name, age, year);
    }

    @Override
    public boolean equals(Object obj) { // parameter type has to be object that is why we have to cast it later in the
        //method

        if (obj == null){
            return false;
        }
        if (obj == this){
            return true;
        }
        if (obj.getClass() != this.getClass()){
            return false; // they cant be equal if they arent in the same class
        }

        Person p = (Person)obj; // casting obj to be a person so we can call obj.name

        return (this.id == p.id &&
                this.name.equals(p.name) &&
                this.age == p.age &&
                this.year == p.year);

    }

    @Override
    public int hashCode() {

        return (id * name.hashCode() * age * year.hashCode());
        //return (name.hashCode() + age + year.hashCode()); // idk if you can just do + they just need to be unique
    }
}
