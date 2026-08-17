public class Main {
    public static void main(String args[])
    {
        Motorcycle cycle1 = new Motorcycle();
        cycle1.setName("Suzuki");
        cycle1.setPrice(5340);
        cycle1.showDetails();

        Motorcycle cycle2 = new Motorcycle ("BMW");
        cycle2.setPrice(12500);
        cycle2.showDetails();

        Motorcycle cycle3 = new Motorcycle("Honda",6700);
        cycle3.showDetails();
    }
}

