public class Seasons{
    public static void main(String args[]){
        YearRange myFirstRange = new YearRange(1314, 2014);
        myFirstRange.setRangeType(1);

        YearRange mySecondRange = new YearRange(1100, 3150);
        mySecondRange.setRangeType(2);

        YearRange myThirdRange = new YearRange(1500, 1890);
        myThirdRange.setRangeType(3);

        System.out.println("List of all years divisible by " + myFirstRange.getFirstDivisor() + " and " + myFirstRange.getSecondDivisor());
        myFirstRange.printYears();

        System.out.println("List of all leap years between " + mySecondRange.getFirstYear() + " and " + mySecondRange.getSecondYear());
        mySecondRange.printYears();

        System.out.println("List all Olympic years between " + myThirdRange.getFirstYear() + " and " + myThirdRange.getSecondYear() + "that are divisble by " + myThirdRange.getFirstDivisor());
        myThirdRange.printYears();


    }
}

class YearRange {
    private int firstYear;
    private int secondYear;
    private int rangeType;
    private int firstDivisor;
    private int secondDivisor;

    public YearRange(int firstYear, int secondYear) {
        this.firstYear = firstYear;
        this.secondYear =secondYear;
    }

    public void setRangeType(int rangeType) {
        this.rangeType = rangeType;
        switch (rangeType){
            case 1:
                firstDivisor = 14;
                secondDivisor = 20;
                break;
            case 2:
                firstDivisor = 4;
                secondDivisor  = 0;
                break;
            case 3:
                firstDivisor = 4;
                secondDivisor = 3;
                break;
            default:
                firstDivisor = 1;
                secondDivisor = 1;
                        
        }
    }

    public int getFirstDivisor(){
        return firstDivisor;
    }

    public int getSecondDivisor(){
        return secondDivisor;
    }

    public int getFirstYear(){
        return firstYear;
    }

    public int getSecondYear(){
        return secondYear;
    }

    public void printYears(){
        switch(rangeType){
            case 1:
                for (int year = firstYear; year <= secondYear; year++){
                    if (year % firstDivisor == 0 && year % secondDivisor == 0){
                        System.out.println(year);
                    }
                }
                break;
            case 2:
                for (int year = firstYear; year <= secondYear; year++){
                    if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
                        System.out.println(year);
                    }
                }
                break;
            case 3:
                for (int year = firstYear; year <= secondYear; year++) {
                    if (year % firstDivisor == 0 && year % 3 == 0) {
                        System.out.println(year);
                    }
                }
                break;    

            }
    }
        
}