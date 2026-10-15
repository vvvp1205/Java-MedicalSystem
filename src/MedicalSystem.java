import java.util.ArrayList;//存储列表
import java.util.List;
/**
 * 让doctor类记录他负责的病人，只是打印分配信息
 * 打印护士来协助病人
 * 关联关系（Association）
 * 1. Patient与Doctor：双向关联
 * 2. Hospital与Patient/Doctor：一对多关联
 * 3. 通过对象引用建立关系
 * 多态：
 * 1. 护士对象可以赋值给Doctor
 * 2.但调用displayInfo()时执行的是护士类的方法
 */

/**
 * 1. Patient
 * 属性（成员变量）：姓名、年龄、ID、是否住院
 * 住院、出院、显示信息
 */
class Patient {
    private String name;//隐秘封装
    private int age;
    private final String id;
    private boolean Admitted;//布尔值，true或false
    private Doctor assignedDoctor; // 分配医生

    // 有参构造
    public Patient(String name, int age, String id) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.Admitted = false; // 默认未住院
        this.assignedDoctor = null; // 默认未分配医生
    }

    // Getter（固定）和 Setter（修改）
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

    public String getId() {
        return id;
    }

    public boolean isAdmitted() {
        return Admitted;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Doctor doctor) {
        this.assignedDoctor = doctor;
    }

    // 病人住院
    public void admit() {
        if (!Admitted) {
            Admitted = true;
            System.out.println(name + " 已成功住院");
        } else {
            System.out.println(name + " 已经在住院状态");
        }
    }

    // 出院
    public void discharge() {
        if (Admitted) {
            Admitted = false;
            System.out.println(name + " 已成功出院");
        } else {
            System.out.println(name + " 当前未住院");
        }
    }

    // 打印病人信息
    public void displayInfo() {
        String status = Admitted ? "已住院" : "未住院";
        String doctorInfo = (assignedDoctor != null) ?
                assignedDoctor.getName() : "未分配";

        System.out.println("* 病人信息 *");
        System.out.println("姓名: " + name);
        System.out.println("年龄: " + age);
        System.out.println("ID: " + id);
        System.out.println("住院状态: " + status);
        System.out.println("负责医生: " + doctorInfo);
        System.out.println("\n");
    }
}

/**
 * 2. Doctor
 * 属性：姓名、科室、职位
 * 分配病人、显示信息
 */
class Doctor {
    //protected修饰符 允许子类访问
    protected String name;
    protected String department;
    protected String position;
    private final List<Patient> assignedPatients; // 医生负责的病人列表

    // 有参构造
    public Doctor(String name, String department, String position) {
        this.name = name;
        this.department = department;
        this.position = position;
        this.assignedPatients = new ArrayList<>();
    }

    // Getter固定
    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    // 为病人分配医生
    public void assignPatient(Patient patient) {
        if (!assignedPatients.contains(patient)) {
            assignedPatients.add(patient);
            patient.setAssignedDoctor(this); // 同时设置病人的医生
            System.out.println("已为病人 " + patient.getName() +
                    " 分配医生 " + this.name);
        } else {
            System.out.println("该病人已经分配了医生 " + this.name);
        }
    }

    // 打印医生信息
    public void displayInfo() {
        System.out.println("* 医生信息 *");
        System.out.println("姓名: " + name);
        System.out.println("科室: " + department);
        System.out.println("职位: " + position);
        System.out.println("负责病人数: " + assignedPatients.size());
        System.out.println("\n");
    }
}

/**
 * 3. Nurse
 * 继承属性：姓名、科室、职位
 * 新增属性：负责的病房
 * 重写：显示信息
 */
class Nurse extends Doctor { //extends继承  protected属性和public
    private String ward; // 护士负责的病房ward（新增）

    // 调用父类构造方法super
    public Nurse(String name, String department, String position) {
        super(name, department, position);
        this.ward = "未分配"; // 默认未分配病房
    }

    // Getter、Setter
    public String getWard() {
        return ward;
    }

    // 分配护士负责的病房
    public void assignWard(String ward) {
        this.ward = ward;
        System.out.println("护士 " + name + " 已分配到 " + ward + " 病房");
    }

    // 重写显示信息：显示护士特有信息
    @Override //注解 重写了父类displayInfo用法
    public void displayInfo() {
        System.out.println("* 护士信息 *");
        System.out.println("姓名: " + name);
        System.out.println("科室: " + department);
        System.out.println("职位: " + position);
        System.out.println("负责病房: \n" + ward);
    }
}

/**
 * 4. Hospital
 * 属性：病人列表、医生列表
 * 方法：添加病人/医生、搜索、显示住院病人
 */
class Hospital {
    private final List<Patient> patients;
    private final List<Doctor> doctors;

    // 构造
    public Hospital() {
        patients = new ArrayList<>();  //初始化patient和doctor到list
        doctors = new ArrayList<>();
    }

    // 添加病人
    public void addPatient(Patient patient) {
        patients.add(patient);
        System.out.println("病人 " + patient.getName() + " 已加到医院");
    }

    // 添加医生（包括护士）
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        System.out.println("医护人员 " + doctor.getName() + " 已加到医院");
    }

    // 根据ID来搜索病人
    public Patient searchPatient(String id) {
        for (Patient patient : patients) {  //for循环遍历
            if (patient.getId().equals(id)) {
                return patient;  //筛选到匹配的病人
            }
        }
        System.out.println("未找到ID为 " + id + " 的病人");
        return null;  //没找到
    }

    // 根据姓名搜索医生/护士
    public Doctor searchDoctor(String name) {
        for (Doctor doctor : doctors) {
            if (doctor.getName().equals(name)) {
                return doctor;  //找医生
            }
        }
        System.out.println("未找到姓名为 " + name + " 的医护人员");
        return null;
    }

    // 显示所有已住院的病人
    public void displayAdmittedPatients() {
        System.out.println("\n* 已住院病人列表 *");
        boolean hasAdmitted = false;  //标记是否存在住院的人

        for (Patient patient : patients) {  //遍历病人
            if (patient.isAdmitted()) {  //如果住院  打印
                System.out.println("- " + patient.getName() +
                        " (ID: " + patient.getId() + ", 年龄: " + patient.getAge() + ")");
                hasAdmitted = true;
            }
        }

        if (!hasAdmitted) {  //如果没有住院
            System.out.println("当前没有住院病人");
        }
        System.out.println("\n");
    }

    // 显示所有医生和护士信息
    public void displayAllMedicalStaff() {
        System.out.println("\n* 所有医护人员信息 *");
        if (doctors.isEmpty()) {
            System.out.println("当前没有医护人员");
        } else {
            for (Doctor doctor : doctors) {
                doctor.displayInfo();
            }
        }
        System.out.println("\n");
    }

    // 访问病人列表（用于测试）
    public List<Patient> getPatients() {
        return patients;
    }

    // 访问医生列表（用于测试）
    public List<Doctor> getDoctors() {
        return doctors;
    }
}

/**
 * 测试医疗管理系统
 */
public class MedicalSystem {
    public static void main(String[] args) {
        System.out.println("=== 医疗管理系统 ===");

        // 创建医院
        Hospital hospital = new Hospital();

        // 创建病人
        Patient patient1 = new Patient("张三", 35, "a001");
        Patient patient2 = new Patient("李四", 42, "a002");
        Patient patient3 = new Patient("王五", 28, "a003");

        // 创建医生，其中一个是护士
        Doctor doctor1 = new Doctor("赵医生", "内科", "主治医生");
        Nurse nurse1 = new Nurse("李护士", "内科", "主管护士");

        // 将病人和医生添加到医院
        System.out.println("\n--- 添加的病人和医护人员 ---");
        hospital.addPatient(patient1);
        hospital.addPatient(patient2);
        hospital.addPatient(patient3);

        hospital.addDoctor(doctor1);
        hospital.addDoctor(nurse1);

        // 病人住院
        System.out.println("\n--- 病人住院 ---");
        patient1.displayInfo(); // 住院前
        patient1.admit();       // 住院
        patient1.displayInfo(); // 住院后

        patient2.admit(); // 第二个病人住院
        // 第三个病人保持未住院状态

        // 为护士分配病房
        System.out.println("\n--- 护士分配病房 ---");
        nurse1.assignWard("301病房");

        // 为病人分配医生
        System.out.println("\n--- 分配医生 ---");
        doctor1.assignPatient(patient1);
        doctor1.assignPatient(patient2);
        nurse1.assignPatient(patient3); // 护士也可以分配病人（继承自医生）

        // 显示所有已住院的病人
        hospital.displayAdmittedPatients();

        // 显示所有医生和护士的信息
        hospital.displayAllMedicalStaff();

        // 病人出院
        System.out.println("\n--- 病人出院 ---");
        patient1.displayInfo(); // 出院前
        patient1.discharge();   // 出院
        patient1.displayInfo(); // 出院后

        // 再次显示已住院病人
        hospital.displayAdmittedPatients();

        // 搜索功能测试
        System.out.println("\n--- 搜索功能测试 ---");
        Patient foundPatient = hospital.searchPatient("P002");
        if (foundPatient != null) {
            System.out.print("找到病人: ");
            foundPatient.displayInfo();
        }

        Doctor foundDoctor = hospital.searchDoctor("赵医生");
        if (foundDoctor != null) {
            System.out.print("找到医护人员: ");
            foundDoctor.displayInfo();
        }

        System.out.println("\n");
    }
}