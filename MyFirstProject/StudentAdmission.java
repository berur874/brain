import java.util.Scanner;
public class StudentAdmission {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---Student Admission---");
        System.out.println();

        System.out.print("Enter Student KCSE Grade:");
        int Grade = scanner.nextInt();
        System.out.print("Enter Student Interview Score:");
        int interview = scanner.nextInt();
        System.out.print("Enter Student Confidence Score:");
        int Confidence = scanner.nextInt();
        
        boolean isAdmitted = checkAdmissionCriteria(Grade, interview, Confidence);

        System.out.println("\n---Admission Decision---");
        if (isAdmitted){
            System.out.println("Congratulation,You Have Been Admitted!");
        }else {
            System.out.println("Not Eligible!");
        }
        scanner.close();

    }
    public static boolean checkAdmissionCriteria(int Grade, int interview, int Confidence){
        boolean goodAcademics = (Grade >=65);
        boolean interviewm = (interview>=6);
        boolean confidence = (Confidence>=5);
        return goodAcademics && interviewm && confidence;
    }
}