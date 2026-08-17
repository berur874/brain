public class Motorcycle {
  private  String name;
  private float price;

  public Motorcycle() { }

  public Motorcycle(String name) {
    this.name = name;
  }

  public Motorcycle(String name, float price) {
    this.name = name;
    this.price = price;
  }

  public void setName(String name){
    this.name = name;
  }

  public void setPrice(float price) {
    this.price = price;
  }
  public String getName() {
    return name;
  }

  public float getPrice(){
    return price;
  }

  public void showDetails() {
    System.out.println("Name:"+name);
    System.out.println("Price:"+price);
  }
} 
