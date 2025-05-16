import java.util.*;
import java.io.*;

// Class of doctor
class Doctor {
    String did, dname, specilist, appoint, doc_qual;
    int droom;

    void new_doctor() {
        Scanner input = new Scanner(System.in);
        System.out.print("id:");
        did = input.nextLine();
        System.out.print("name:");
        dname = input.nextLine();
        System.out.print("specilization:");
        specilist = input.nextLine();
        System.out.print("work time:");
        appoint = input.nextLine();
        System.out.print("qualification:");
        doc_qual = input.nextLine();
        System.out.print("room no.:");
        droom = input.nextInt();
    }

    void doctor_info() {
        System.out.println(did + "\t" + dname + "  \t" + specilist + "     \t" + appoint + "    \t" + doc_qual + "       \t" + droom);
    }
}

class Patient {
    public String patientID;
    public String name;
    public int age;
    public String diagnosis;
    public String doctorAssigned;
    public int roomNumber;

    public Patient(String patientID, String name, int age, String diagnosis, String doctorAssigned, int roomNumber) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.diagnosis = diagnosis;
        this.doctorAssigned = doctorAssigned;
        this.roomNumber = roomNumber;
    }

    public void displayPatientInfo() {
        System.out.println(patientID + "\t" + name + "\t" + age + "\t" + diagnosis + "\t" + doctorAssigned + "\t" + roomNumber);
    }
}
class Node {
    public Patient data;
    public Node next;

    public Node(Patient data) {
        this.data = data;
        this.next = null;
    }
}
class LinkedList {
    public Node head;
    public LinkedList() {
        this.head = null;
    }
    public void addPatient(Patient patient) {
        Node newNode = new Node(patient);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }
    public void displayPatients() {
        if (head == null) {
            System.out.println("No patients in the system.");
            return;
        }
        Node temp = head;
        while (temp != null) {
            temp.data.displayPatientInfo();
            temp = temp.next;
        }
    }
    public void removePatient(String patientID) {
        if (head == null) {
            System.out.println("No patients in the system.");
            return;
        }
        if (head.data.patientID.equals(patientID)) {
            head = head.next; 
            System.out.println("Patient with ID " + patientID + " removed successfully.");
            return;
        }
        Node temp = head;
        while (temp.next != null) {
            if (temp.next.data.patientID.equals(patientID)) {
                temp.next = temp.next.next; 
                System.out.println("Patient with ID " + patientID + " removed successfully.");
                return;
            }
            temp = temp.next;
        }
        System.out.println("Patient with ID " + patientID + " not found.");
    }
}

class Surgery {
    String surgeryID;
    String patientName;
    String surgeryType;
    String doctorAssigned;
    String surgeryDate;
    int roomNumber;

    public Surgery(String surgeryID, String patientName, String surgeryType, String doctorAssigned, String surgeryDate, int roomNumber) {
        this.surgeryID = surgeryID;
        this.patientName = patientName;
        this.surgeryType = surgeryType;
        this.doctorAssigned = doctorAssigned;
        this.surgeryDate = surgeryDate;
        this.roomNumber = roomNumber;
    }

    public void showSurgeryDetails() {
        System.out.println("Surgery ID: " + surgeryID);
        System.out.println("Patient Name: " + patientName);
        System.out.println("Surgery Type: " + surgeryType);
        System.out.println("Doctor: " + doctorAssigned);
        System.out.println("Date: " + surgeryDate);
        System.out.println("Room Number: " + roomNumber);
    }
}

class SurgeryQueue {
    public Surgery[] surgeryQueue;
    public int front, rear, size;
    public static final int CAPACITY = 10;

    public SurgeryQueue() {
        surgeryQueue = new Surgery[CAPACITY];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void addSurgery(Surgery surgery) {
        if (size == CAPACITY) {
            System.out.println("Queue is full, cannot add surgery.");
        } else {
            rear = (rear + 1) % CAPACITY;
            surgeryQueue[rear] = surgery;
            size++;
            System.out.println("Surgery added!");
        }
    }

    public void showAllSurgeries() {
        if (size == 0) {
            System.out.println("No surgeries scheduled.");
            return;
        }

        int tempFront = front;
        for (int i = 0; i < size; i++) {
            surgeryQueue[tempFront].showSurgeryDetails();
            System.out.println("----------------------");
            tempFront = (tempFront + 1) % CAPACITY;
        }
    }

    public void removeSurgery(String surgeryID) {
        if (size == 0) {
            System.out.println("No surgeries scheduled.");
            return;
        }

        boolean found = false;
        Surgery[] newQueue = new Surgery[CAPACITY];
        int newSize = 0;

        for (int i = 0; i < size; i++) {
            int index = (front + i) % CAPACITY;
            if (surgeryQueue[index].surgeryID.equals(surgeryID)) {
                found = true;
                continue; 
            }
            newQueue[newSize++] = surgeryQueue[index];
        }

        if (found) {
            surgeryQueue = newQueue;
            front = 0;
            rear = newSize - 1;
            size = newSize;
            System.out.println("Surgery with ID " + surgeryID + " removed successfully.");
        } else {
            System.out.println("Surgery with ID " + surgeryID + " not found.");
        }
    }
}

public class project {
    public static void saveDoctorsToFile(Doctor[] doctors, int count) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("doctors.txt"))) {
            for (int i = 0; i < count; i++) {
                writer.write(doctors[i].did + "," + doctors[i].dname + "," + doctors[i].specilist + "," + doctors[i].appoint + "," + doctors[i].doc_qual + "," + doctors[i].droom);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving doctors to file: " + e.getMessage());
        }
    }

    public static int loadDoctorsFromFile(Doctor[] doctors) {
        int count = 0;
        try (Scanner fileScanner = new Scanner(new File("doctors.txt"))) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                doctors[count] = new Doctor();
                doctors[count].did = data[0];
                doctors[count].dname = data[1];
                doctors[count].specilist = data[2];
                doctors[count].appoint = data[3];
                doctors[count].doc_qual = data[4];
                doctors[count].droom = Integer.parseInt(data[5]);
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error loading doctors from file: " + e.getMessage());
        }
        return count;
    }

    public static void removeDoctor(Doctor[] doctors, int count, String doctorId) {
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (doctors[i] != null && doctors[i].did.equals(doctorId)) {
                found = true;
                for (int j = i; j < count - 1; j++) {
                    doctors[j] = doctors[j + 1];
                }
                doctors[count - 1] = null;
                System.out.println("Doctor with ID " + doctorId + " removed successfully.");
                return;
            }
        }
        if (!found) {
            System.out.println("Doctor with ID " + doctorId + " not found.");
        }
    }

    public static void savePatientsToFile(LinkedList patientList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("patients.txt"))) {
            Node temp = patientList.head;
            while (temp != null) {
                Patient p = temp.data;
                writer.write(p.patientID + "," + p.name + "," + p.age + "," + p.diagnosis + "," + p.doctorAssigned + "," + p.roomNumber);
                writer.newLine();
                temp = temp.next;
            }
        } catch (IOException e) {
            System.out.println("Error saving patients to file: " + e.getMessage());
        }
    }

    public static void loadPatientsFromFile(LinkedList patientList) {
        try (Scanner fileScanner = new Scanner(new File("patients.txt"))) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                Patient p = new Patient(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4], Integer.parseInt(data[5]));
                patientList.addPatient(p);
            }
        } catch (IOException e) {
            System.out.println("Error loading patients from file: " + e.getMessage());
        }
    }

    public static void saveSurgeriesToFile(SurgeryQueue surgeryQueue) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("surgeries.txt"))) {
            int tempFront = surgeryQueue.front;
            for (int i = 0; i < surgeryQueue.size; i++) {
                Surgery s = surgeryQueue.surgeryQueue[tempFront];
                writer.write(s.surgeryID + "," + s.patientName + "," + s.surgeryType + "," + s.doctorAssigned + "," + s.surgeryDate + "," + s.roomNumber);
                writer.newLine();
                tempFront = (tempFront + 1) % SurgeryQueue.CAPACITY;
            }
        } catch (IOException e) {
            System.out.println("Error saving surgeries to file: " + e.getMessage());
        }
    }

    public static void loadSurgeriesFromFile(SurgeryQueue surgeryQueue) {
        try (Scanner fileScanner = new Scanner(new File("surgeries.txt"))) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                Surgery s = new Surgery(data[0], data[1], data[2], data[3], data[4], Integer.parseInt(data[5]));
                surgeryQueue.addSurgery(s);
            }
        } catch (IOException e) {
            System.out.println("Error loading surgeries from file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SurgeryQueue surgeryQueue = new SurgeryQueue();
        LinkedList patientList = new LinkedList();
        Doctor[] d = new Doctor[25];
        for (int i = 0; i < 25; i++) d[i] = new Doctor();
        int count1 = loadDoctorsFromFile(d);
        loadPatientsFromFile(patientList);
        loadSurgeriesFromFile(surgeryQueue);
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println("            *** Welcome to Hospital Management System Project in Java ***");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("\n                                    MAIN MENU");
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("1.Doctors  2. Patients  3.Surgery  4.Exit ");
            System.out.println("-----------------------------------------------------------------------------------");
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("1. Add New Doctor\n2. View All Doctors\n3. Remove Doctor");
                    int docChoice = input.nextInt();
                    if (docChoice == 1) {
                        d[count1].new_doctor();
                        count1++;
                        saveDoctorsToFile(d, count1);
                    } else if (docChoice == 2) {
                        for (int i = 0; i < count1; i++) {
                            d[i].doctor_info();
                        }
                    } else if (docChoice == 3) {
                        input.nextLine();
                        System.out.print("Enter Doctor ID to Remove: ");
                        String doctorId = input.nextLine();
                        removeDoctor(d, count1, doctorId);
                        saveDoctorsToFile(d, count1 - 1);
                        count1--;
                    }
                    break;
                case 2:
                    System.out.println("1. Add New Patient\n2. View All Patients\n3. Remove Patient");
                    int patChoice = input.nextInt();
                    if (patChoice == 1) {
                        input.nextLine();
                        System.out.print("Enter Patient ID: ");
                        String pid = input.nextLine();
                        System.out.print("Enter Name: ");
                        String pname = input.nextLine();
                        System.out.print("Enter Age: ");
                        int page = input.nextInt();
                        input.nextLine();
                        System.out.print("Enter Diagnosis: ");
                        String pdiag = input.nextLine();
                        System.out.print("Enter Doctor Assigned: ");
                        String doc = input.nextLine();
                        System.out.print("Enter Room Number: ");
                        int room = input.nextInt();
                        Patient p = new Patient(pid, pname, page, pdiag, doc, room);
                        patientList.addPatient(p);
                        savePatientsToFile(patientList);
                    } else if (patChoice == 2) {
                        patientList.displayPatients();
                    } else if (patChoice == 3) {
                        input.nextLine();
                        System.out.print("Enter Patient ID to Remove: ");
                        String patientID = input.nextLine();
                        patientList.removePatient(patientID);
                        savePatientsToFile(patientList);
                    }
                    break;
                case 3:
                    System.out.println("1. Schedule Surgery\n2. View All Surgeries\n3. Remove Surgery");
                    int surgChoice = input.nextInt();
                    if (surgChoice == 1) {
                        input.nextLine();
                        System.out.print("Enter Surgery ID: ");
                        String surgID = input.nextLine();
                        System.out.print("Enter Patient Name: ");
                        String patName = input.nextLine();
                        System.out.print("Enter Surgery Type: ");
                        String surgType = input.nextLine();
                        System.out.print("Enter Doctor Assigned: ");
                        String docAssigned = input.nextLine();
                        System.out.print("Enter Surgery Date: ");
                        String surgDate = input.nextLine();
                        System.out.print("Enter Room Number: ");
                        int surgRoom = input.nextInt();
                        Surgery s = new Surgery(surgID, patName, surgType, docAssigned, surgDate, surgRoom);
                        surgeryQueue.addSurgery(s);
                        saveSurgeriesToFile(surgeryQueue);
                    } else if (surgChoice == 2) {
                        surgeryQueue.showAllSurgeries();
                    } else if (surgChoice == 3) {
                        input.nextLine();
                        System.out.print("Enter Surgery ID to Remove: ");
                        String surgeryID = input.nextLine();
                        surgeryQueue.removeSurgery(surgeryID);
                        saveSurgeriesToFile(surgeryQueue);
                    }
                    break;
                case 4:
                    System.out.println("Thanks for Visiting");
                    System.exit(0);
            }
        }
    }
}
